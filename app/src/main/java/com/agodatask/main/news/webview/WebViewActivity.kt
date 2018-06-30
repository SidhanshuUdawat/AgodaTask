package com.agodatask.main.news.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.agodatask.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    companion object {
        private const val URL_TO_LOAD = "url_to_load"

        @JvmStatic
        fun getIntent(context: Context, urlToLoad: String): Intent {
            var mIntent = Intent(context, WebViewActivity::class.java)
            mIntent.putExtra(URL_TO_LOAD, urlToLoad)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initToolbar()
        loadWebView()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun loadWebView() {
        val urlToLoad = intent.getStringExtra(URL_TO_LOAD)

        customWebView.settings.javaScriptEnabled = true
        customWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        customWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }
        customWebView.loadUrl(urlToLoad)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.customWebView.canGoBack()) {
            this.customWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
