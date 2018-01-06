//$$ZZOO8DNMMMMMNNMMMNMMMMMMNMNDDD8ODDNNNDDDNNMNNND8DNNNMMNNNM
//NDZ7$ZZZZOO8DDNNNNNNNNN8OZZZOOO8D888O8NNNNMNND88NMMNNNMMNNNM
//MMMNNDO$$$ZZZOOOO888OZZZZ$ZOOOO8O$8DD88DDDD88NMMMMMNNNMMNNNM
//NMMMMMMMNNNDD88OOOZ$DD8OO8OZ$ZOZZOOO88DDDODNMMMMNMMNNNMMNNNM
//DOODNMMMMMMMMMMMNOO8DDDDNDOOZOO88O888D88DD$DNMMMNNNNNNMMNNNM
//MMNND888DNMMMMNOZZ78OZOOO8O88O888D8DDDD8DO8OONMMNNNNNNMNNNNM
//88DNMMMMNNNNDDZZ$ZOOZOZZZZOO8D888888D8O8D8D8OONNNNNNNNMMNNNM
//DDDDDD8888888ZZZOOOOOZ$$$ZZO8O88OOZOO$ZOZO8O888MNNNNNNMNNNNM
//DDDDDDDDDNMN8ZOO88$OZZZ??Z7I$$$ZOZ7IZ$?Z$I$OO8ONNNNNNNMNNNNM
//DDDDDDDDDMND8Z8DDZ+II=?+=7+???+=I?==?=$IZ=+78ONDNNNNNNMNNNNM
//DDDDDD88DMMD8O8D8I==~~~~~~~~=~~~~=:::~~~+:~=7D8DNNNNNNNNNNNM
//DDD8DD88DMNDOO8DZ+~:::~:::::::::::::::::::::788NNNNNNNNMNNNM
//DDD8D88DDNN8DD88Z+~~~:::,,:,,::::::::::::::~7O8NNNNNNNNMNNNM
//DDD8888DNMNDNDDD?=~~:=+==~::::::,:::::::::::IO8NNNNNNNNNNNNM
//DDDDDD8DNNNDNND$===+++?7$7Z?==~~~+IZ$$$I?::~~OODNNNNNNNMNNNM
//DDDDDDDDNNN~=D8$=~?I7I+??+?7?+==+???+=====~~:8ODNNNNNNNMNNNN
//DDDDDD8DNM$I??$$$D?+?$O88$7$8N8ONO$$77I+=ID=~Z?+NNNNNNNNNDNM
//DDDDDDDDNN7+==$$7D+===~=+?7I7N8N7I$?8$?I7?7MZ=??DNNNNNNNNNNM
//DDDDDDDDNNN==?$++?==~==+??+=$=:~D=?????+++=N+==?DNDDNNNNNNNN
//DDDDDDDDNNN8~++=~~Z?:~~~~~=I+:,,+=====~~~~O~++=NNNNDNNNNNNNN
//DDDDDDDDNNND+===~~~::::::~=~~:,,:~?$ZZ$$7=::+=7NNNDDNNNNNNNN
//DDDDDDDDNNND8:~~==~~:::~+=~:~:,,::~?=~::::::+=NNNNDDNNNNNNNN
//DDDDDDDDNNNDD++==++====+~=+==~~~~~~====~~~~~~NNNNNDDNNNNNNNN
//DDDDDDDNNNNDNDD===++++=~~~~?I$7I?+==~==+=~=~$NNNNNNDNNNNNNNN
//DDNDDDDNNNNDNDD+=====+?+=~~~~~===~=~==++==~~NNNNNNNDNNNNNNNN
//DDDDNDDNNNNDNDD$?+=+==+INZZZZ$$$ZZZO$+====~7NNNNNNNDDNNNNNNN
//DDDNDDDNNNNDNDDNI?+++==~=+?I?~~:=++=~======NNNNNNNNNNNNNNNNN
//NNDDNDNNNNNDNDDD+II??+===+I?====+?=~==++++DNNNNNNDDDNNNNNNNN
//DDNDDNNNNNNDNDD:+?7II?===~=+III?+=====++?NNNNNNNNNNDNNNNNNNN
//DNNNDDNNNNNNND~.=+II7I?=~~~~~~~~~~~~=+?8NNNNNNNNNNDDNNNNDNNN
//NNNDNNNNNNDDDD,.=+?II7II+==~~~==~~=+?+:DNNNNNNNNNNDDNNNNDNNN
//NNNNNNNNNDND$:..~++??I7II7IIII??II7?+=,~NNNNNNNNNNNDNNNNDNNN
//NNNNNNNNND$I,....~+++?II777777777I?+=~,:$NNNNNNNNNNDDNNNDDNN
//NNNNNNO$7+++:......=++??III777I???+=~..:$INNNNNNNNNDDNNDDDNN
//ND$7++++++??~.......,++?????????+++:...,777$NNNNNNDDDNND8DDD
//++++?????+???.........:++??I??++=:.....:777777NNNDDDDDND88DD
//++????????II+............++++=:,.......:$$77$777$8888DD888DD
//++???????II+=...........,:::,,........,~$$7777777777ZZOO88OO
//			威哥保佑，玄学除BUG

//websocket 服务器main函数

package main

import (
	"os"
	"os/exec"
	"strings"
	"log"
	"path/filepath"
	"websocket_broker/src/websocketserver"
)


func main() {
	//设置当前路径
	file, err := exec.LookPath(os.Args[0])
	if err != nil {
		log.Panic("Look Path Error")
	}
	path, err := filepath.Abs(file)
	if err != nil {
		log.Panic("Look Path Error")
	}
	println("Woring path:", substr(path, 0, strings.LastIndex(path, "/")))
	os.Chdir(substr(path, 0, strings.LastIndex(path, "/")))
	var server websocketserver.WebSocketServer
	//配置server
	server.SettingServer()
	//开始server
	server.StartServer()
}

func substr(s string, pos, length int) string {
	runes := []rune(s)
	l := pos + length
	if l > len(runes) {
		l = len(runes)
	}
	return string(runes[pos:l])
}

