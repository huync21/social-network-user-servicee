# social-network-user-service
Là service quản lý user, phục vụ cho các internal service trong hệ thống call tới, sử dụng basic auth

![img.png](img.png)

# usersdb

![img_1.png](img_1.png)

# Hướng dẫn khởi chạy
**Bước 1:** Run _docker-compose.yml_

**Bước 2:** Run _SocialNetworkUserServiceMigrationApplication_ trong module _social-network-user-service-migration_

**Bước 3:** Run _SocialNetworkUserServiceApplication_ trong module _social-network-user-service-application_

# Postman collection
Để ở file Social-network-user-service.postman_collection.json trong project

# Basic auth và phân quyền các endpoint:

![img_2.png](img_2.png)

các server có basic auth quyền admin mới được call api thêm quyền cho các user.


# Mô tả API
**1. POST** - /api/v1/users/authorize

**Mô tả**

API phân quyền user (sau này dự định sẽ để con authorization server chọc vào db usersdb lấy quyền lên để 
gen access token cho mobile-gateway có những api thêm sửa xóa tài khoản cá nhân trên app mạng xã hội (ROLE_USER) 
và back-office-gateway có những api cho phép admin lý user(ROLE_ADMIN))

**curl:**

    curl --location --request POST 'localhost:8500/social-network-user-service/api/v1/users/authorize' \
    --header 'Authorization: Basic YmFjay1vZmZpY2UtdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140' \
    --data-raw '{
    "user_id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
    "roles": ["ROLE_ADMIN", "ROLE_USER"]
    }'

**Authentication:**

    Basic Auth
    Username: back-office-usecase-service
    Password: password

**Request Body:**

```json
{
    "user_id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
    "roles": ["ROLE_ADMIN", "ROLE_USER"]
}
```
}

**Response Body:**
```json
{
  "data": {
    "id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
    "username": "huy2110",
    "avatar": null,
    "description": null,
    "roles": [
      {
        "name": "ROLE_ADMIN"
      },
      {
        "name": "ROLE_USER"
      }
    ]
  },
  "meta": {
    "code": "200"
  }
}
```

**Corner cases**
- Sử dụng basic auth chỉ có quyền user mà không có quyền admin thì sẽ không dùng được api phân quyền này:

Basic auth: 

username: social-network-usecase-service

password: password

response:

```json
{
    "timestamp": "2022-12-20T18:39:13.300+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/social-network-user-service/api/v1/users/authorize"
}
```


- Không tìm thấy user gắn với user trong request body:
```json
{
    "meta": {
        "code": "400000",
        "message": "User not found",
        "internal_message": "User not found"
    }
}
```
- Role trong body không hợp lệ

request:
```json
{
    "user_id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
    "roles": ["ROLE_ADMIN", "ROLE_USERrrrrrrrrrr"]
}
```


response:
```json
{
    "meta": {
        "code": "400004",
        "message": "Role doesn't exist",
        "internal_message": "Role ROLE_USERrrrrrrrrrr doesn't exist"
    }
}
```

**2. POST** - /api/v1/users?keyword={keyword}

**Mô tả**

Api search list user theo tên (dự định nếu có time sẽ đẩy lên es để tăng tốc độ search cũng như có hỗ trợ nhiều chức năng search)

**curl:**

    curl --location --request GET 'localhost:8500/social-network-user-service/api/v1/users?keyword=huy' \
    --header 'Authorization: Basic c29jaWFsLW5ldHdvcmstdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140'

**Authentication:**

    Basic auth
    username: social-network-usecase-service
    password: password

**Query Param**

_keyword:_ Từ khóa tìm kiếm user (required)

**Response Body:**
```json
{
    "data": [
        {
            "id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
            "username": "huy2110",
            "avatar": null,
            "description": null,
            "roles": [
                {
                    "name": "ROLE_ADMIN"
                },
                {
                    "name": "ROLE_USER"
                }
            ]
        }
    ],
    "meta": {
        "code": "200"
    }
}

```
**3. POST** - /api/v1/users

**Mô tả**

API tạo user 

**curl:**

    curl --location --request POST 'localhost:8500/social-network-user-service/api/v1/users' \
    --header 'Authorization: Basic c29jaWFsLW5ldHdvcmstdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140' \
    --data-raw '{
    "username": "huy2110",
    "password": "Huy_211002",
    "avatar": "link.com",
    "description": "string"
    }'

**Authentication:**
- Basic Auth
- Username: social-network-usecase-service
- Password: password

**Request Body:**
```json
{
  "username": "huy2110",
  "password": "Huy_211002",
  "avatar": "link.com",
  "description": "string"
}
```
}

**Response Body:**
```json
{
  "data": {
    "id": "7fb02392-74bf-4daf-b43c-fc52645528f9",
    "username": "huy21100",
    "avatar": "link.com",
    "description": "string",
    "roles": [
      {
        "name": "ROLE_USER"
      }
    ]
  },
  "meta": {
    "code": "200"
  }
}
```

**Corner cases**
- User đã tồn tại:
```json
{
  "meta": {
    "code": "400002",
    "message": "User name already existed",
    "internal_message": "User name already existed"
  }
}
```
- Password không đúng định dạng
```json
{
    "meta": {
        "code": "400001",
        "message": "Invalid Parameter",
        "internal_message": "password: Password must be at least 8 characters in length.,Password must contain at least 1 uppercase characters.,Password must contain at least 1 digit characters.,Password must contain at least 1 special characters.\n"
    }
}
```
- Thiếu username:
```json
{
    "meta": {
        "code": "400001",
        "message": "Invalid Parameter",
        "internal_message": "username: must not be blank\n"
    }
}
```

**4. DELETE** - /api/v1/users?userId={userId}

**Mô tả**

API xóa user (xóa mềm)

**curl:**

    curl --location --request DELETE 'localhost:8500/social-network-user-service/api/v1/users?userId=e4bb8bdf-abe7-4cbd-815b-031fda68ccee' \
    --header 'Authorization: Basic YmFjay1vZmZpY2UtdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140'

**Authentication:**
- Basic Auth
- Username: social-network-usecase-service
- Password: password

**Query param**

userId

**Response Body:**
```json
{
  "meta": {
    "code": "200"
  }
}
```

**Corner cases**
- Không tìm thấy user gắn với userid:
```json
{
  "meta": {
    "code": "400000",
    "message": "User not found",
    "internal_message": "User not found"
  }
}
```

**5. PUT** - /api/v1/users

**Mô tả**

API sửa user ( đã có async log xuống db khi change user name)

**curl:**

    curl --location --request PUT 'localhost:8500/social-network-user-service/api/v1/users' \
    --header 'Authorization: Basic c29jaWFsLW5ldHdvcmstdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140' \
    --data-raw '{
    "id": "8f2bd405-ab85-4d24-b20b-e5124496229c",
    "username": "huy21102000"
    }'

**Authentication:**
- Basic Auth
- Username: social-network-usecase-service
- Password: password

**Request Body:**
```json
{
  "id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
  "username": "huy21102000",
  "avatar": "abc.com"
}
```
}

**Response Body:**
```json
{
  "data": {
    "id": "0b8ac4a8-6e11-4259-8d88-1cd7e6ce50cf",
    "username": "huy21102000",
    "avatar": "abc.com",
    "description": null,
    "roles": [
      {
        "name": "ROLE_ADMIN"
      },
      {
        "name": "ROLE_USER"
      }
    ]
  },
  "meta": {
    "code": "200"
  }
}
```

**Corner cases**
- Không tìm thấy user ứng với user id:
```json
{
  "meta": {
    "code": "400000",
    "message": "User not found",
    "internal_message": "User not found"
  }
}
```

**6. PUT** - /api/v1/users/change-password

**Mô tả**

API đổi pasword

**curl:**

    curl --location --request PUT 'localhost:8500/social-network-user-service/api/v1/users/change-password' \
    --header 'Authorization: Basic c29jaWFsLW5ldHdvcmstdXNlY2FzZS1zZXJ2aWNlOnBhc3N3b3Jk' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=A545B35A9E978937D0345C13FE76B140' \
    --data-raw '{
    "old_password": "Huy_211002",
    "new_password": "Huy_21102000",
    "user_id": "d0b1bc9a-7f66-4ef8-be5b-c12ce2095e64"
    }'

**Authentication:**
- Basic Auth
- Username: social-network-usecase-service
- Password: password

**Request Body:**
```json
{
  "old_password": "Huy_211002",
  "new_password": "Huy_21102000",
  "user_id": "d0b1bc9a-7f66-4ef8-be5b-c12ce2095e64"
}
```
}

**Response Body:**
```json
{
  "meta": {
    "code": "200"
  }
}
```

**Corner cases**
- Không tìm thấy user ứng với user id:
```json
{
  "meta": {
    "code": "400000",
    "message": "User not found",
    "internal_message": "User not found"
  }
}
```
- Truyền sai pass cũ:
```json
{
  "meta": {
    "code": "400003",
    "message": "Your old password is incorrect",
    "internal_message": "Your old password is incorrect"
  }
}
```
- Pass mới sai định dạng:
```json
{
  "meta": {
    "code": "400001",
    "message": "Invalid Parameter",
    "internal_message": "newPassword: Password must be at least 8 characters in length.,Password must contain at least 1 uppercase characters.,Password must contain at least 1 digit characters.,Password must contain at least 1 special characters.\n"
  }
}
```
# Testing

Intergration test sử dụng junit4 và rest assured.

# Nếu có thêm time sẽ làm tiếp:

**1. Oauth 2.**

Thêm Authorization Server (sử dụng keycloak hoặc auth0 hoặc thư viện authorization server của spring để build server)

Thêm mobile-gateway đóng vai trò là api gateway public ra ngoài internet để mobile app sử dụng, đồng thời đóng vai trò là
Resource server 

**2. Thêm test coverage**

Thêm Test Coverage để đánh giá độ hiệu quả của unittest.

**3. Thêm các service để phục vụ các business của mạng xã hội**


- Ví dụ như thêm 1 cái topic user update để mỗi khi người dùng update thông
tin cá nhân(tiểu sử, thay đổi avatar,...) thì cho user service bắn event lên topic đó,
và mỗi khi có thêm nghiệp vụ thì ta có thể cho thêm service lắng nghe đến topic để
gửi noti cho bạn bè, người follow của user đó mà ko cần sửa code bên user-service -> tăng 
khả năng mở rộng; hơn nữa cũng giúp code chạy async, người dùng update thông tin cá nhân 
mà ko bị blocking bởi việc xử lý của các service noti, chỉ việc bắn event qua topic rồi xử lý tiếp nghiệp vụ
trả về response.

- Bổ sung thêm các thông tin cho user profile

- Thêm service quản lý bài post(bắn noti khi có bài viết mới hoặc comment), gửi friend request, thêm service quản lý
connect giữa các user, group user, service quản lý media files, chat message,...

- Đẩy dữ liệu user lên es để search nhanh hơn và có thêm nhiều chức năng search

- Thêm service usecase và gateway cho backoffice.

# Nếu lên productio sẽ cần thêm:

- Trước hết là phải đảm bảo được về chất lượng, đã đúng với requirement, đầy đủ unit test, test tích hợp, để đảm bảo
cover được hết nghiệp vụ.
- Request môi trường trên production (db, kafka, es,...)
- Thêm config cho môi trường production(datasource đến db trên prod, config call tới các service khác nếu có, kafka topic...)
- Mã hóa password của basic auth
