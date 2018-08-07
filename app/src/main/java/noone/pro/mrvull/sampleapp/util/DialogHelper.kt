package noone.pro.mrvull.sampleapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import noone.pro.mrvull.sampleapp.R

class DialogHelper(private var context: Context) {
    @SuppressLint("InflateParams")
    fun showDialog(error: String) {
        val dialog = AlertDialog.Builder(context).create()
        val dialogView = LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_layout, null)
        val btnClose = dialogView.findViewById<ImageView>(R.id.btnCloseTutorial)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        val content = dialogView.findViewById<TextView>(R.id.txtWrappingTutorialContent)
        content.movementMethod = ScrollingMovementMethod()
        content.text = error
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.show()
    }
}