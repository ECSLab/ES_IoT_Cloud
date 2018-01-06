package websocketserver

import (
	"testing"
	"log"
	"fmt"
	"database/sql"
	//"io"
	"os"
	"bufio"
	"io"
)

func TestJsonTool_SolveDataJson(t *testing.T) {
	str := []byte(`{"api_key": "eslabtest", "device_id": "7", "data": "{\"pm2.5\": 17.60,\"pm10\": 31.90}", "time":"2017-12-25 16:54:08"} `)
	js  := JsonTool{}
	var a PakInfo
	var err error
	if a,err = js.SolveDataJson(str); err != nil{
		log.Println("error", err)
	}else {
		fmt.Println(a.Api_key)
		fmt.Println(a.Device_id)
		fmt.Println(a.Data)
		fmt.Println(a.Time)
	}

	sr, _ := js.MakeDataJson(a)
	fmt.Println(string(sr))
}

func TestJsonTool_MakeSettingJson(t *testing.T) {
	var set Setting
	set.User = "root"
	set.Password = "3375"
	set.Ipaddr = "127.0.0.1"
	set.Port = "3306"
	set.DbName = "IoT_platform"
	set.Code = "utf8"
	js  := JsonTool{}
	byt, _ := js.MakeSettingJson(set)
	println(string(byt))
}

func TestJsonTool_SloveSeetingJson(t *testing.T) {
	fil, err := os.Open("dbsetting.json")
	if err != nil {
		fmt.Println("dbsetting.json", err)
		return
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
			return
		}
	}
	log.Println(str)

	js  := JsonTool{}
	a, _ := js.SloveSeetingJson([]byte(str))
	println(a.User)
	println(a.Ipaddr)
	println(a.Password)

}

func TestDBTools_CheckDevice(t *testing.T) {
	loadsetting()
	var dd = DBTools{new(sql.DB)}
	dd.InitDataBase()
	res,_:= dd.CheckDevice("eslabtest", "3")
	log.Println(res)
}

func TestDBTools_InsertData(t *testing.T) {
	loadsetting()
	var dd = DBTools{new(sql.DB)}
	dd.InitDataBase()
	res,_ := dd.InsertData(1, `{"pm2.5": 17.60,"pm10": 31.90}`, "2017-10-27 20:23:31")
	log.Println(res)
}