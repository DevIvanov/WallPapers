package com.example.ivanov_p3.common.base

import android.annotation.SuppressLint
import android.app.Service
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.ivanov_p3.R
import com.example.ivanov_p3.util.view.hideKeyboard
import com.example.ivanov_p3.util.view.showAlertDialog
import com.example.ivanov_p3.util.view.toast
import java.io.File
import java.io.FileOutputStream
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseFragment (@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private var connectivity : ConnectivityManager? = null
    private var info : NetworkInfo? = null


    fun toast(message: Int) {
        requireContext().toast(message)
    }

    fun showAlertDialog(title: String, message: String){
        requireContext().showAlertDialog(title, message)
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun backPressed () {
        var backPressedTime:Long = 0
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            val backToast: Toast = Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
            if (backPressedTime + 1000 > System.currentTimeMillis()) {
                backToast.cancel()
                activity?.finish()
            } else {
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM HH:mm")

        return simpleDateFormat.format(Date())
    }

    fun checkInternetConnection () : Boolean{
        connectivity = requireActivity().getSystemService(Service.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo
            return !(info == null || info!!.state != NetworkInfo.State.CONNECTED)
        }
        return false
    }

    fun getUrl (searchQuery: String): URL?{
        val searchStringNoSpaces = searchQuery.replace(" ", "+")
        val key = "AIzaSyCmp7XwRBMvUmPXxUtNzEr22BvaZb4sQJw"
        val searchId = "faaf876d27c79cad7"
        val urlString =
            "https://www.googleapis.com/customsearch/v1?q=$searchStringNoSpaces&key=$key&cx=$searchId&alt=json"
        var url: URL? = null
        try {
            url = URL(urlString)
        } catch (e: MalformedURLException) {
            Log.e(ContentValues.TAG, "ERROR converting String to URL $e")
        }
        Log.d(ContentValues.TAG, "Url = $urlString")
        return url
    }

    fun shareImage(imageBitmap: Bitmap) {
        try {
            val file = File(requireActivity().externalCacheDir,"image.png")
            val fOut = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val imageUri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.ivanov_p3.ui.fragment.DetailsFragment.provider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, imageUri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share image via"))

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LOG", e.message.toString())
        }
    }

    fun darkenBackground(color: Float) {
        val lp: WindowManager.LayoutParams = requireActivity().window.attributes
        lp.alpha = color
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        requireActivity().window.attributes = lp
    }

    fun setWallpaper(imageBitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(requireActivity())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(
                imageBitmap,
                null,
                true,
                WallpaperManager.FLAG_SYSTEM
            )
            toast(R.string.wallpaper_set)
        } else {
            toast(R.string.unsupported_version)
        }
    }

    fun setSplashScreen(imageBitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(requireActivity())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(
                imageBitmap,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )
            toast(R.string.splash_screen_set)
        } else {
            toast(R.string.unsupported_version)
        }
    }

    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
}