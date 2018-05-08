package com.example.apuitiza.consumewebservice_from_visualstudio.Util;

import android.view.View;

public interface RecyclerViewItemClickListener {
    // void onItemClick(View view, int position); Por si quiero implementar el Click en un recyclerView
    void onItemLongClick(View view, int position);
}
