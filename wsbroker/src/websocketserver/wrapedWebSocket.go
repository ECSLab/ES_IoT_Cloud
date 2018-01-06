package websocketserver

import (
	"golang.org/x/net/websocket"
	"log"
)

type wrapedWebSocket struct {
	wsconn *websocket.Conn
	dbtool *DBTools
	recv   chan []byte	//接收控制信息管道
	send   chan []byte  //接收设备数据管道
}

func (wss *wrapedWebSocket) StartWebSocketUnit() {
	//TODO
	//设备数量过多可能出问题,需要测试
	wss.dbtool = new(DBTools)
	wss.dbtool.InitDataBase()

	js := JsonTool{}	//解析josn
	//处理消息循环
	for {
		select {
		//收到控制命令
		case command := <-wss.send:
			log.Println(command)
			log.Println(wss.wsconn.RemoteAddr().String())
			if err := websocket.Message.Send(wss.wsconn, string(command)); err != nil {
				log.Println("Can't send command to device", err.Error())
				error_log.Println("Can't send command to device", err.Error())
				log.Println(err)
				error_log.Println(err)
				break
			} else {
				log.Println("Send a command to dev： " + wss.wsconn.RemoteAddr().String())
				normal_log.Println("Send a command to dev： " + wss.wsconn.RemoteAddr().String())
			}
		//收到设备上传信息
		case data := <-wss.recv:
			log.Println("Receive a dataPack from dev:",wss.wsconn.RemoteAddr().String())
			normal_log.Println("Receive a dataPack from dev",wss.wsconn.RemoteAddr().String())
			if res, err := js.SolveDataJson(data); err != nil {
				//解析失败
				log.Println("SOLVE_JSON_ERROR:", err.Error())
				error_log.Println("SOLVE_JSON_ERROR:", err.Error())
				websocket.Message.Send(wss.wsconn, "SERVER_SOLVE_JSON_ERROR:"+ err.Error())
			} else {
				//解析成功
				if dev_id, erro := wss.dbtool.CheckDevice(res.Api_key, res.Device_id); erro != nil {
					//校验设备失败
					log.Println("CHECK_DEV_ERROR:", err.Error() )
					error_log.Println("CHECK_DEV_ERROR:", err.Error())
					websocket.Message.Send(wss.wsconn, "SERVER_CHECK_DEV_ERROR:" + err.Error())
				} else if (dev_id == -1) {
					//设备API_KEY和ID不符
					log.Println("API_DEVID_NOT_PAIRED")
					error_log.Println("API_DEVID_NOT_PAIRED")
					websocket.Message.Send(wss.wsconn, "API_DEVID_NOT_PAIRED")
				} else {
					//向数据库添加设备数据失败
					if _, err := wss.dbtool.InsertData(dev_id, res.Data, res.Time); err != nil {
						log.Println("ADD_DATA_ERROR:", err.Error())
						error_log.Println("ADD_DATA_ERROR:", err.Error())
						websocket.Message.Send(wss.wsconn, "SERVER_ADD_DATA_ERROR:"+ err.Error())
					}else {
						//向数据库添加设备数据成功
						log.Println("ADD_DATA_SUCCESS")
						normal_log.Println("ADD_DATA_SUCCESS")
						websocket.Message.Send(wss.wsconn, "SERVER_ADD_DATA_SUCCESS")
					}
				}
			}
		}
	}
}

func (wss *wrapedWebSocket) SendCommandToDevice(msg []byte) {
	//向send管道写入要向设备发送的控制信息
	wss.send <- msg
}

func (wss *wrapedWebSocket) RecvDataFromDevice() {
	//TODO
	//考虑加入超时断线功能
	for {
		var data []byte
		if err := websocket.Message.Receive(wss.wsconn, &data); err != nil {
			log.Println("Can't receive data from device")
			error_log.Println("Can't receive data from device")
			break
		}else{
			//向recv管道写入从设备收到的数据
			wss.recv <- data
		}
	}
	wss.wsconn.Close()
	log.Println("Closed:", wss.wsconn.RemoteAddr().String())
	normal_log.Println("Closed:", wss.wsconn.RemoteAddr().String())
}


