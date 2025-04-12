Tests:

`docker run --rm -p 8080:8080 -v "${PWD}:/app" -w /app golang go run main.go`  

GET localhost:8080/api/products

returns

200 OK with body:
```json
[
  {
    "id":1,"name":"Laptop",
    "description":"1TB SSD",
    "price":4999.99,
    "category_id":1,
    "category":"Elektronika"
  },
  {
    "id":2,
    "name":"Smartfon",
    "description":"128GB",
    "price":2499.99,
    "category_id":1,
    "category":"Elektronika"
  }
]
```

available other HTTP requests: [GET /id, POST /, PUT /id, DELETE /id]  
