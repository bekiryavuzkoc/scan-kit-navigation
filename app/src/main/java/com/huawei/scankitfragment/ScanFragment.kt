package com.huawei.scankitfragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import kotlinx.android.synthetic.main.fragment_scan.*
import java.lang.Exception


class ScanFragment : Fragment(), MainActivity.ScanKitActivityResult {

    companion object
    {
        private const val DEFINED_CODE = 222
        private const val REQUEST_CODE_SCAN = 0X01
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFragmentListener(this)
        scanQRCodeBtn.setOnClickListener{qrOperation()}
    }


    private fun qrOperation(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            val list = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(list, DEFINED_CODE)
        }else{
            ScanUtil.startScan(activity, REQUEST_CODE_SCAN, HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) return
        else if (requestCode == DEFINED_CODE) {
            // Call the barcode scanning view in Default View mode.
            ScanUtil.startScan(activity, REQUEST_CODE_SCAN, HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) { return }
        // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
        else if (requestCode == REQUEST_CODE_SCAN) {
            when (val obj: Any = data.getParcelableExtra(ScanUtil.RESULT)) {
                is HmsScan -> {
                    if (!TextUtils.isEmpty(obj.getOriginalValue())) {
                            Toast.makeText(activity, obj.getOriginalValue(), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Please scan the correct qr code", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            }
        }
    }


}