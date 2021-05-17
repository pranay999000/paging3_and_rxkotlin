package com.example.atgandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.atgandroid.R
import com.google.android.material.snackbar.Snackbar

class FooterViewHolder(parent: ViewGroup, retry:() -> Unit):
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.footer, parent, false)) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    private val snackbar: Snackbar = Snackbar.make(parent, "Network problem", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { retry.invoke() }

    fun bind(loadState: LoadState) {

            progressBar.isVisible = loadState is LoadState.Loading
//            snackbar.show() = loadState !is LoadState.Loading
            if (loadState !is LoadState.Loading) {
                snackbar.show()
            } else {
                snackbar.dismiss()
            }
    }
}