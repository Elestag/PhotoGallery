package com.bignerdranch.android.photogallery

import android.util.Log
import com.bignerdranch.android.photogallery.api.PhotoResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val TAG = "Deserializer"

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {

        val jsonObject: JsonObject? = json?.asJsonObject

        //Log.d(TAG, "JsonObject: $jsonObject")

        val jsonObjectPhotos = jsonObject?.get("photos")
       // Log.d(TAG, "JsonObjectPhotos: $jsonObjectPhotos")

        val jsonObjectPhoto = jsonObjectPhotos?.asJsonObject?.get("photo")?.asJsonArray
       // Log.d(TAG, "JsonObjectPhoto: $jsonObjectPhoto")

        val listItems: MutableList<GalleryItem> = mutableListOf()

        var galleryItemTitle = ""
        var galleryItemId = ""
        var galleryItemUrl = ""
        var galleryItemOwner = ""

        if (jsonObjectPhoto != null) {
            for (element in 0 until jsonObjectPhoto.size()) {

                galleryItemTitle =
                    jsonObjectPhoto[element].asJsonObject.get("title").toString().replace("\"", "")
                galleryItemId =
                    jsonObjectPhoto[element].asJsonObject.get("id").toString().replace("\"", "")
                galleryItemUrl =
                    jsonObjectPhoto[element].asJsonObject.get("url_s").toString().replace("\"", "")
                galleryItemOwner =
                    jsonObjectPhoto[element].asJsonObject.get("owner").toString().replace("\"", "")

//                Log.d(
//                    TAG,
//                    "galleryTitle[$element]: $galleryItemTitle, galleryId[$element]: $galleryItemId, galleryURL[$element]: $galleryItemUrl, galleryOwner[$galleryItemOwner]"
//                )

                listItems += GalleryItem(
                    galleryItemTitle,
                    galleryItemId,
                    galleryItemUrl,
                    galleryItemOwner
                )
            }


        }
        val photoResponse = PhotoResponse()
        photoResponse.galleryItems = listItems

        return photoResponse
    }

}