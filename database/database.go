package database

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"log"
	"os"
)

var Database *gorm.DB

func coalesce(first string, second string) string {
	if first != "" {
		return first
	} else {
		return second
	}
}

var host = coalesce(os.Getenv("schedules_database_host"), "localhost")
var port = coalesce(os.Getenv("schedules_database_port"), "5432")
var user = coalesce(os.Getenv("schedules_database_user"), "golang")
var password = coalesce(os.Getenv("schedules_database_password"), "golang")

func InitializeDatabase() {
	db, err := gorm.Open(
		"postgres",
		"host="+host+
			" port="+port+
			" user="+user+
			" password="+password+
			" dbname=gwynder_schedules sslmode=disable",
	)
	if err != nil {
		log.Fatal(err)
	} else {
		Database = db
	}

}
