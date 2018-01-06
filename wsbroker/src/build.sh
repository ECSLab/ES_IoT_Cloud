CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o ../bin/main_amd64_linux main.go 
CGO_ENABLED=0 GOOS=linux GOARCH=386 go build -o ../bin/main_i386_linux main.go 

CGO_ENABLED=0 GOOS=windows GOARCH=amd64 go build -o ../bin/main_amd64_windows.exe main.go
CGO_ENABLED=0 GOOS=windows GOARCH=386 go build -o ../bin/main_i386_windows.exe main.go

CGO_ENABLED=0 GOOS=darwin GOARCH=amd64 go build -o ../bin/main_amd64_darwin main.go
CGO_ENABLED=0 GOOS=darwin GOARCH=386 go build -o ../bin/main_i386_darwin main.go
