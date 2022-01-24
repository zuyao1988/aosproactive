package com.liveperson.sample.app.push

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.ConnectionResult as GoogleConnectionResult

object PushUtils {

	fun isGooglePlayServicesAvailable(context: Context): Boolean {
		val googleApiAvailability = GoogleApiAvailability.getInstance()
		val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
		return true //resultCode == GoogleConnectionResult.SUCCESS;
	}
}
