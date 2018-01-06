package websocketserver


type Setting struct{
	User string
	Password string
	Ipaddr string
	Port string
	DbName string
	Code string
}
//用户名 密码 IP地址 端口  数据库名称  数据库编码方式

func (st *Setting) GetUser() string{
	return st.User
}

func (st *Setting) GetPassWord() string{
	return st.Password
}

func (st *Setting) GetDbName() string{
	return st.DbName
}

func (st *Setting) GetIpAddr() string{
	return st.Ipaddr
}

func (st *Setting) GetPort() string{
	return st.Port
}

func (st *Setting) GetCode() string{
	return st.Code
}