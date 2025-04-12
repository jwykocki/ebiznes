package main

import (
	"crud-echo-gorm/config"
	"crud-echo-gorm/models"
	"crud-echo-gorm/routes"
	"crud-echo-gorm/utils"
	"fmt"
)

func main() {

	e := routes.InitRoutes()

	db, err := config.InitDB()
	if err != nil {
		fmt.Println("Failed to connect to database:", err)
		return
	}

	err = models.MigrateModels(db)
	if err != nil {
		fmt.Println("Failed to migrate models:", err)
		return
	}

	utils.SeedDB()
	e.Logger.Fatal(e.Start(":8080"))
}
