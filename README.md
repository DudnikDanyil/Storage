# Storage
This is a Spring-based RESTful API that enables users to store, upload, download, modify, and delete files in a cloud storage system. Users can upload files to the storage and access them through their personal accounts.

## Key technologies
- [Spring Boot](https://spring.io/)
- [Maven](https://maven.apache.org/)
- [JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org)

## Overview
Our platform offers users the ability to register and authenticate, after which they can save their files securely on our platform. Users can also use our search functionality to quickly locate the files they need, and even edit the names of their files directly in the storage system.

## API requests

**Registration requests:**

**- Registration**
<pre>
    @PostMapping("/api/register")
</pre>

Input parameters as a POST-query:
<pre>
{
    email : "****",
    password: "****"
}
</pre>
Return parameters:
<pre>
[
{
"data":"true"
}
]
</pre>
And also JWT-token

**Authorization requests:**

**- Authorization**
<pre>
    @PostMapping("/api/auth")
</pre>

Input parameters as a POST-query:
<pre>
{
    email : "****",
    password: "****"
}
</pre>
Return parameters:
<pre>
[
{
"nameFile":null,
"dateFile":null,
"sizeFile":null,
"typeFile":null,
"data":"true"
}
]
</pre>
If a person has files on the server:
<pre>
[
{
"nameFile":Test.txt,
"dateFile":00.00.0000,
"sizeFile":23,
"typeFile":.txt,
"data":"true"
}
...
]
</pre>

**- Authentication**
<pre>
    @GetMapping("/api/authentic")
</pre>
Input parameters as a POST-query:

JWT-token

Return parameters:
<pre>
[
{
"nameFile":null,
"dateFile":null,
"sizeFile":null,
"typeFile":null,
"data":"true"
}
]
</pre>
If a person has files on the server:
<pre>
[
{
"nameFile":Test.txt,
"dateFile":00.00.0000,
"sizeFile":23,
"typeFile":.txt,
"data":"true"
}
...
]
</pre>

**File requests:**

**- Delete**
<pre>
    @DeleteMapping("/api/deleteFile")
</pre>
Input parameters as a DELETE-query:
<pre>
/api/deleteFile?nameFail=****
</pre>
Return parameters:
<pre>
{
"fileIsDeleted":true
}
</pre>

**- Download**
<pre>
    @GetMapping(value = "/api/download")
</pre>
Input parameters as a GET-query:
<pre>
/api/downloadFile?nameFail=****
</pre>
Return parameters:

File download in progress

**- Editing**
<pre>
    @PutMapping(value = "/api/editing")
</pre>














