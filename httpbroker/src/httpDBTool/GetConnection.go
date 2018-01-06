package httpDBTool

import (
	"github.com/BurntSushi/toml"
	"os"
	"io/ioutil"
	"database/sql"
	"fmt"
)

type DatabaseConfig struct {
	Hostsip string
	Username string
	Password string
	DBname string
}

var conf DatabaseConfig

func checkErr(err error){
	if (err != nil){
		panic(err)
	}
}

func ReadConfig()(*DatabaseConfig){
	path := "../config/conf.toml"

	config := new(DatabaseConfig)

	var (
		fp *os.File
		fcontent []byte
	)

	var err error

	fp, err = os.Open(path)
	checkErr(err)

	fcontent, err = ioutil.ReadAll(fp)
	checkErr(err)

	err = toml.Unmarshal(fcontent, config)
	checkErr(err)

	return config
}

func Connect()(*sql.DB){
	config := ReadConfig()
	str := config.Username + ":" + config.Password + "@tcp(" + config.Hostsip + ")/" +
		config.DBname + "?charset=utf8"

	fmt.Println(str)

	db, err := sql.Open("mysql", str)
	checkErr(err)
	return db
}
