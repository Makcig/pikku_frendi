package com.example.pikku_frendi.data

data class User (val Name: String = "", val Status: String = "",
                 val whoami: String = "", val about: String = "")

/*        @PrimaryKey(autoGenerate = true)
        val ID: Integer,

        @ColumnInfo(name = "login")
        val login: String,

        @ColumnInfo(name = "password")
        val password: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "picture_dir")
        val profilepicture: String,

        @ColumnInfo(name = "image_storage")
        val imageStorageId: Int
*/