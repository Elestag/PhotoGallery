package com.bignerdranch.android.photogallery

import android.util.Log
import com.bignerdranch.android.photogallery.api.PhotoResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val TAG = "FlickrFetchr"

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {

        val jsonObject: JsonObject? = json?.asJsonObject

       // Log.d(TAG,"JsonObject: $jsonObject")

        val jsonObjectPhotos = jsonObject?.get("photos")
       // Log.d(TAG,"JsonObjectPhotos: $jsonObjectPhotos")

        val jsonObjectPhoto = jsonObjectPhotos?.asJsonObject?.get("photo")?.asJsonArray
      //  Log.d(TAG,"JsonObjectPhoto: $jsonObjectPhoto")

        var listItems: List<GalleryItem> = mutableListOf()

        if (jsonObjectPhoto != null) {
            for (element in 0 until jsonObjectPhoto.size()){
                val galleryItemTitle:String = jsonObjectPhoto[element].asJsonObject.get("title").toString()

                val galleryItemId:String = jsonObjectPhoto[element].asJsonObject.get("id").toString()
                val galleryItemUrl:String = jsonObjectPhoto[element].asJsonObject.get("url_s").toString().replace("\"","")
               // Log.d(TAG,"galleryTitle[$element]: $galleryItemTitle, galleryId[$element]: $galleryItemId, galleryURL[$element]: $galleryItemUrl")
                listItems += GalleryItem(galleryItemTitle,galleryItemId,galleryItemUrl)
            }


        }
        val photoResponse = PhotoResponse()


        photoResponse.galleryItems = listItems

        return photoResponse
    }

}