package com.example.ktoradapter.common

import android.widget.Toast
import com.example.ktoradapter.MainActivity


fun MainActivity.MakeToast(messages: String)
    = Toast.makeText(this, messages, Toast.LENGTH_LONG).show()
