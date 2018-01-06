package websocketserver

import (
	"golang.org/x/net/websocket"
	"log"
	"net/http"
	"time"
	"fmt"
	"os"
	"bufio"
	"io"
)

//数据库配置信息
var(
	hostdbinfo = "%s:%s@tcp(%s:%s)/%s?charset=%s" //数据库地址
	// 用户名 密码 IP地址 端口  数据库名称  数据库编码方式
)

//log文件信息
var(
	log_name = "websocketserver.log"
	normal_log *log.Logger
	warning_log *log.Logger
	error_log *log.Logger
	panic_log *log.Logger
)

type WebSocketServer struct {
	devs   map[string]*wrapedWebSocket
	dbtool *DBTools
}

//加载配置文件
func loadsetting(){

	//打开log文件
	logFile,err  := os.Create(log_name)
	if err != nil {
		log.Fatalln("open file error !")
		panic("open log file error ")
	}
	//正常log输出
	normal_log = log.New(logFile,"[INFO]",log.LstdFlags)
	//警告
	warning_log = log.New(logFile,"[WARN]",log.LstdFlags)
	//错误log输出
	error_log = log.New(logFile,"[ERRO]",log.LstdFlags)
	//panic log输出
	panic_log = log.New(logFile,"[PANI]",log.LstdFlags)

	//解析数据库配置文件
	os.Open("dbsetting.json")
	fil, err := os.Open("dbsetting.json")
	if err != nil {
		fmt.Println("dbsetting.json", err)
		dir, _ := os.Getwd()
		panic_log.Println(err)
		panic("Open database setting error, working dir: " + dir)
	}
	defer fil.Close()
	buf := bufio.NewReader(fil)
	var str string
	for {
		line, _,err := buf.ReadLine()
		str += string(line)
		if err != nil {
			if err == io.EOF {
				break
			}
			panic_log.Println(err)
			panic("Solve database setting error")
		}
	}
	log.Println("Init DBSetting:" + str)
	js  := JsonTool{}
	if a, err := js.SloveSeetingJson([]byte(str)); err != nil{
		panic_log.Println(err)
		panic("Solve database setting error")
	}else {
		hostdbinfo = fmt.Sprintf(hostdbinfo, a.User, a.Password, a.Ipaddr, a.Port, a.DbName, a.Code)
		log.Println("DBsetting:", hostdbinfo)
	}
}

//初始化哈希表，设置路由
func (wss *WebSocketServer) SettingServer() {
	//加载配置文件
	loadsetting()
	//初始化变量
	wss.devs = make(map[string]*wrapedWebSocket)
	wss.dbtool = new(DBTools)
	wss.dbtool.InitDataBase()
	go wss.cleaner()
	//设置路由
	http.Handle("/", websocket.Handler(wss.addClient))
	http.HandleFunc("/upload", wss.SendMsgToClient)
}

//启动服务器，开始监听端口
func (wss *WebSocketServer) StartServer() {
	log.Println("Start listening port 9000")
	normal_log.Println("Start listening port 9000")
	if err := http.ListenAndServe(":9000", nil); err != nil {
		panic_log.Println("ListenAndServe:", err)
		panic(err)
	}

}

//将连接进的设备wrapedWebSocket添加到 HASHTable中
func (wss *WebSocketServer) addClient(ws *websocket.Conn) {
	var jsonbytes []byte
	websocket.Message.Receive(ws, &jsonbytes)
	js := JsonTool{}
	//解析JSON
	if res, err := js.SolveDataJson(jsonbytes); err != nil{
		//解析失败
		log.Println("SOLVE_JSON_ERROR:", err.Error())
		error_log.Println("SOLVE_JSON_ERROR:", err.Error())
		websocket.Message.Send(ws, "SERVER_SOLVE_JSON_ERROR:" + err.Error())
		ws.Close()
	}else{
		log.Println("Get Data from " +  ws.RemoteAddr().String())
		normal_log.Println("Get Data from " +  ws.RemoteAddr().String())
		//认证待接入设备
		if id, err := wss.dbtool.CheckDevice(res.Api_key, res.Device_id); err != nil || id == -1{
			log.Println("AUTH ERROR" + res.Api_key + res.Device_id)
			error_log.Println("AUTH ERROR" + res.Api_key + res.Device_id)
			//回执失败给认证设备
			websocket.Message.Send(ws, "SERVER_AUTH_ERROR")
			ws.Close()
		}else {
			//认证成功后创建wrapedWebsocket
			conn := wrapedWebSocket{ws, nil, make(chan []byte), make(chan []byte)}
			wss.devs[res.Api_key + res.Device_id ] = &conn
			//回执成功给认证设备
			websocket.Message.Send(ws, "SERVER_AUTH_SUCESS")
			log.Println("Estanblish :" + conn.wsconn.RemoteAddr().String())
			normal_log.Println("Estanblish :" + conn.wsconn.RemoteAddr().String())
			log.Println("Now dev num : " ,len(wss.devs))
			normal_log.Println("Now dev num : " ,len(wss.devs))
			//创建协程来监听设备消息的转发
			go conn.RecvDataFromDevice()
			//开始处理接受到的消息
			conn.StartWebSocketUnit()
			}
		}
}


//给客户（硬件端）发送数据
func (wss *WebSocketServer) SendMsgToClient(w http.ResponseWriter, r *http.Request) {
	log.Println(r.RemoteAddr + "  METHOD:" + r.Method)
	normal_log.Println(r.RemoteAddr + "  METHOD:" + r.Method)

	r.ParseForm()
	//POST方法
	if r.Method == "POST" {
		if res, err := wss.dbtool.CheckDevice(r.Form["api_key"][0], r.Form["device_id"][0]); err != nil{
			fmt.Fprintf(w, "AUTH_ERROR")
			log.Println("AUTH_ERROR: " + r.RemoteAddr )
			error_log.Println("AUTH_ERROR: " + r.RemoteAddr )
		}else if res == -1{
			fmt.Fprintf(w, "AUTH_FAIL")
			log.Println("AUTH_FAIL: " + r.RemoteAddr)
			error_log.Println("AUTH_FAIL: " + r.RemoteAddr)
		}else {
			if _, ok := wss.devs[r.Form["api_key"][0] + r.Form["device_id"][0]]; ok!=true{
				fmt.Fprintf(w, "DEV_NOT_ONLINE")
				log.Println("DEV_NOT_ONLINE")
				normal_log.Println("DEV_NOT_ONLINE")
			}else {
				wss.devs[r.Form["api_key"][0] + r.Form["device_id"][0]].SendCommandToDevice([]byte(r.Form["data"][0]))
				fmt.Fprintf(w, "PUBLISH_SUCCESS")
				log.Println("PUBLISH_SUCCESS: " + r.RemoteAddr)
				normal_log.Println("PUBLISH_SUCCESS: " + r.RemoteAddr)
			}

		}
	}
}


//维护HASH表， 将失效的WebSocket连接清除，3s维护一次
func (wss *WebSocketServer) cleaner(){
	for {
		for name, iter := range wss.devs{
			if err := websocket.Message.Send(iter.wsconn, "HEART_BEAT"); err != nil{
				delete(wss.devs, name)
				log.Println("Del a device:" + name)
				normal_log.Println("Del a device:" + name)
			}
		}
		time.Sleep(3 * time.Second)
	}
}