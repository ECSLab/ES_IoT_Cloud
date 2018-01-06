package httpDBTool

import (
	"database/sql"
	"log"
)

func AddData(db *sql.DB, device_num int, data_value string, add_time string)(error){
	stmt, err := db.Prepare(`INSERT t_data (device_id, data_value, create_time) values(?,?,?)`)
	if err != nil {
		return err
	}
	defer stmt.Close()

	res, err := stmt.Exec(device_num, data_value, add_time)
	if err != nil {
		return err
	}

	id, err := res.LastInsertId()
	if err != nil {
		return err
	}
	log.Println("Last insert id: ", id)
	return nil
}