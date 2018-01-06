package httpDBTool

import (
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"errors"
	"log"
	"fmt"
)


func CheckDevice(db *sql.DB, apiKey string, devNum string)(bool, error) {
	str := "SELECT * FROM t_device_info WHERE project_id = " +
		"(SELECT id FROM t_project_info WHERE api_key = \"" + apiKey + "\")" +
			"and number = \"" + devNum + "\""

	log.Println(str)

	paired, err := db.Query(str)
	checkErr(err)
	if paired.Next() {
		fmt.Println("Pass")
		return true, nil
	}else{
		fmt.Println("API_DEVICE_NOT_PAIRED")
		return false, errors.New("API_DEVICE_NOT_PAIRED")
	}
}
