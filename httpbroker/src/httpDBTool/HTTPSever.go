package httpDBTool

import (
	"log"
	"fmt"
	"net/http"
	"strconv"
    "database/sql"
)

var port string = "9006"
var db *sql.DB

func SettingServer(){
	http.HandleFunc("/", Process)
}

func StartServer(){
    db = Connect()
	log.Println("Start listening port ", port)
	if err := http.ListenAndServe(":"+port, nil); err != nil {
		log.Fatal("ListenAndSever: ", err)
	}
}

func Process(w http.ResponseWriter, r *http.Request) {
	fmt.Println("method:", r.Method)
	if r.Method == "POST"{
		r.ParseForm()
		var(
			apikey string
			devnum string
		)
		apikey = r.FormValue("Api_key")
		devnum = r.FormValue("Device_id")
		if result, err := CheckDevice(db, apikey, devnum); err != nil || result == false{
			log.Println("API_DEVID_NOT_PAIRED")
			fmt.Fprintf(w, "API_DEVID_NOT_PAIRED\n")
		} else {
			log.Println("PAIRED")
			fmt.Fprintf(w, "PAIRED\n")



			datavalue := r.FormValue("Data")
			deviceid, err := strconv.Atoi(r.FormValue("Dev_num"))
            addtime := r.FormValue("Create_time")

			if (err != nil || datavalue == ""){
				log.Println("GET_DATA_ERROR")
				fmt.Fprintf(w, "Get data error\n")
				return
			}

			if err := AddData(db, deviceid, datavalue, addtime); err != nil {
				log.Println("SERVER_ADD_DATA_ERROR")
				fmt.Fprintln(w, "Add value failed\n")
			} else {
				log.Println("PUBLISH_SUCCESS")
				fmt.Fprintln(w,"Add value success\n")
			}
		}
	}
}