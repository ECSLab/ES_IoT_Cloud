package websocketserver

import (
	"database/sql"
	_"github.com/go-sql-driver/mysql"
	"log"
)


type DBTools struct{
	dbconn *sql.DB
}

//初始化数据库
func (dt *DBTools) InitDataBase(){
	dt.dbconn = new(sql.DB)

	//打开数据库
	if db, err := sql.Open("mysql", hostdbinfo);err!=nil{
		panic_log.Println(err)
		panic("Open DataBase Error")
	}else{
		dt.dbconn = db
	}
}

//重新；连接数据库
func (dt *DBTools) ReConnDB(){
	defer dt.dbconn.Close();
	if db, err := sql.Open("mysql", hostdbinfo);err!=nil{
		panic_log.Println(err)
		panic("Open DataBase Error")
	}else{
		dt.dbconn = db
	}
	normal_log.Println("Reconnected Database")
	log.Println("Reconnected Database")
}

//检查设备api_key和device_number是否匹配,并返回这个设备的id
func (dt *DBTools) CheckDevice(apikey string, dev_num string) (int32, error){
	if db, err := sql.Open("mysql", hostdbinfo);err!=nil{
		panic_log.Println(err)
		panic("Open DataBase Error")
	}else{
		dt.dbconn = db
	}
	defer dt.dbconn.Close();

	var(
		idres int32 //返回设备在数据表单中的ID
		err1 error
	)

	if res, err := dt.dbconn.Query(`SELECT id FROM t_device_info WHERE project_id=` +
		`(SELECT id FROM t_project_info WHERE api_key=?) and number=?`, apikey, dev_num); err!= nil{
		log.Println(err)
		error_log.Println(err)
		idres = -1
		err1 = err
	}else{
		if res.Next(){
			var id int32
			res.Scan(&id)
			idres = id
			err1 = err
		}else {
			idres = -1
			err1 = err
		}
	}
	return idres, err1
}

//传感器信息插入
func (dt *DBTools) InsertData(device_id int32, data_value string, create_time string)(bool, error){
	if db, err := sql.Open("mysql", hostdbinfo);err!=nil{
		panic_log.Println(err)
		panic("Open DataBase Error" )
	}else{
		dt.dbconn = db
	}
	defer dt.dbconn.Close();
	insert_handle, _ := dt.dbconn.Prepare(`INSERT INTO t_data(device_id,data_value,create_time)VALUES(?, ?, ?)`)
	if _, err := insert_handle.Exec(device_id, data_value, create_time); err!= nil{
		return false, err
	}else {
		return true,nil
	}
}

//TODO
//SQL防注入处理