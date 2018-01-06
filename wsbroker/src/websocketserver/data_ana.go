package websocketserver

import (
	"encoding/json"
)

//Json解析类别
type JsonTool struct {

}

//解析数据包方法
func (js *JsonTool) SolveDataJson(str []byte) (PakInfo, error){
	var pkg PakInfo
	if err := json.Unmarshal(str, &pkg); err!=nil{
			return PakInfo{"", "", "", ""}, err
	}else{
		return pkg, err
	}
}

//生成数据包JSON
func (js *JsonTool) MakeDataJson(info PakInfo) ([]byte, error){
	if b, err := json.Marshal(info); err!=nil{
		return nil, err
	}else {
		return b, err
	}
}


//解析设置方法
func (js *JsonTool) SloveSeetingJson(str []byte) (Setting, error){
	var set Setting
	if err := json.Unmarshal(str, &set); err!=nil{
		return Setting{"", "", "", "", "", ""}, err
	}else{
	return set, err
	}
}

//生成设置Json
func (js *JsonTool) MakeSettingJson(set Setting) ([]byte, error){
	if b, err := json.Marshal(set); err!=nil{
		return nil, err
	}else {
		return b, err
	}
}
