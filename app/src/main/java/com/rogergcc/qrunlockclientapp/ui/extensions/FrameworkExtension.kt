package com.rogergcc.qrunlockclientapp.ui.extensions

import com.google.gson.Gson


/**
 * Created on marzo.
 * year 2024 .
 */
inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)
