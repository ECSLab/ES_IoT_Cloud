package websocketserver

import "log"

//数据包类型
type PakInfo struct{
	Api_key string
	Device_id string
	Data string
	Time string
}

func (pk *PakInfo)  ShowInfo(){
	log.Println(pk.Device_id + pk.Data)
}

func (pk *PakInfo) GetApikey() string{
	return pk.Api_key
}

func (pk *PakInfo) GetDeviceId() string{
	return pk.Device_id
}

func (pk *PakInfo) GetData() string{
	return pk.Data
}

func (pk *PakInfo) GetTime() string{
	return pk.Time
}