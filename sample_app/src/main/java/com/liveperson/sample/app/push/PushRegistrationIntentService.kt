package com.liveperson.sample.app.push

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.liveperson.infra.ICallback
import com.liveperson.infra.PushType
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.sample.app.push.PushUtils.isGooglePlayServicesAvailable
import com.liveperson.sample.app.utils.SampleAppStorage
import com.liveperson.sample.app.utils.SampleAppUtils

class PushRegistrationIntentService : IntentService(TAG) {

	override fun onHandleIntent(intent: Intent?) {
		Log.d(TAG, "onHandleIntent: registering the token to pusher")

		if (isGooglePlayServicesAvailable(this)) {
			FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
				if (!task.isSuccessful) {
					Log.w(TAG, "getInstanceId failed", task.exception)
					return@OnCompleteListener
				}

				// Get new Instance ID token
				val token = task.result!!.token
				registerLPPusher(baseContext, token, PushType.FCM)
			})
		}
	}

	private fun registerLPPusher(context: Context, token: String, pushType: PushType) {
		val brandId = SampleAppStorage.getInstance(context).account
		Log.i(TAG, "registerLPPusher: $token")
		LivePerson.registerLPPusher(brandId, SampleAppStorage.SDK_SAMPLE_FCM_APP_ID, token, pushType, SampleAppUtils.createLPAuthParams(context), object : ICallback<Void, Exception> {
			override fun onSuccess(value: Void?) {
				Log.d(TAG, "Registration to Pusher completed successfully")
			}

			override fun onError(exception: Exception?) {
				Log.e(TAG, "Registration to Pusher failed ${exception?.message}")
			}
		})
	}

	private companion object {
		private val TAG = PushRegistrationIntentService::class.java.simpleName
	}
}
