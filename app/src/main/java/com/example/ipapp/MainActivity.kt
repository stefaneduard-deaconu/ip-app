package com.example.ipapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ipapp.model.PAR
import com.example.ipapp.ui.home.HomeFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_docs.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    internal lateinit var db:OpenHelper
    internal var lstPAR:List<PAR> = ArrayList<PAR>()



    fun docNew(v: View) {
        text_docs.text = "new doc"
    }
    fun docSync(v: View) {
        text_docs.text = "sync docs"
    }
    fun docAddPhoto(v: View) {
        // called after we have the photo saved, always
    }
    fun docDelete(v: View) {

    }
    fun docUpdate(v: View) {

    }
    fun userLogin(v: View) {

    }
    fun userRegister(v: View) {

    }
    fun userUpdate(v: View) {

    }
    fun userDelete(v: View) {

    }
    // ??? when we logout, shouldn't the local files be deleted?
    fun userLogout(v: View) {
        // Maybe PASS for this version of the app
    }

    lateinit var navView: BottomNavigationView
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    fun loadLoginFragment(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToLoginFragment()
        navController.navigate(action)
    }
    fun loadRegisterFragment(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToRegisterFragment()
        navController.navigate(action)
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File? {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    fun pressCameraButtonAction(v: View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                var photoFile: File? = null
                try{
                   photoFile = createImageFile()
                } catch (ex: IOException) { }
                // Continue only if the File was successfully created
                photoFile?.also { Log.d("Intent","Eroare1")
                    val photoURI = getUriForFile(
                        applicationContext,
                        "com.example.ip_app.fileprovider",
                        photoFile
                    )
                    Log.d("Intent","Eroare")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            picture.setImageURI(Uri.parse(currentPhotoPath))

            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            val bytes = File(currentPhotoPath).readBytes()
            val base64 = Base64.getEncoder().encodeToString(bytes)
            val ext = MimeTypeMap.getFileExtensionFromUrl(currentPhotoPath)
            val decoded = Base64.getDecoder().decode(base64)
            val bitmap2 = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
//            TODO add this to the sqlite DB that you're going to implement :D
            picture.setImageBitmap(bitmap2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = OpenHelper(this)

        navView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
