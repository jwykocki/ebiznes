package models

import "gorm.io/gorm"

var DB *gorm.DB

func SetDB(db *gorm.DB) {
	DB = db
}

func MigrateModels(db *gorm.DB) error {
	err := db.AutoMigrate(&Category{}, &Product{})
	if err != nil {
		return err
	}
	SetDB(db)
	return nil
}
