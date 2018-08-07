package noone.pro.mrvull.sampleapp.util

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogHelper(context: Context) {
    private val progressDialog: ProgressDialog = ProgressDialog(context)
    fun createProgressDialog(message: String) {
        progressDialog.setMessage(message)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    fun destroyProgressDialog() {
        progressDialog.dismiss()
    }
}