package database

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"log"
)

var Database *gorm.DB

func InitializeDatabase() {
	db, err := gorm.Open(
		"postgres",
		"host=localhost port=5432 user=golang password=golang dbname=gwynder_schedules sslmode=disable",
	)
	if err != nil {
		log.Fatal(err)
	} else {
		log.Println("Connected to database")
		ApplyMigration(db)
		log.Println("Applied database migration")
		Database = db
	}

}
