package com.inventors.jd.keeper.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventors.jd.keeper.R;
import com.inventors.jd.keeper.activities.MainActivity;
import com.inventors.jd.keeper.activities.UpdatePostActivity;
import com.inventors.jd.keeper.activities.WebViewActivity;
import com.inventors.jd.keeper.models.Post;

import java.util.ArrayList;


/**
 * Created by jd on 04-Aug-18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context ctx;

    private ArrayList<Post> posts = new ArrayList<>();

    private String url, videoTitle;

//    private YouTubeExtractor ytEx;

    public PostAdapter(final Context ctx, ArrayList<Post> posts) {
        this.ctx = ctx;
        this.posts = posts;

//        ytEx = new YouTubeExtractor(ctx) {
//            @Override
//            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
//                if (sparseArray != null) {
//                    int tag = 22;
//                    String downloadUrl = sparseArray.get(tag).getUrl();
//                    url = downloadUrl;
//                    Toast.makeText(ctx, "" + videoMeta.getTitle(), Toast.LENGTH_SHORT).show();
//                    videoTitle = videoMeta.getTitle();
//
//                    Log.d("URL", downloadUrl);
//                    downloadVideo(url);
//                }
//            }
//        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post post = posts.get(position);

        holder.txtTitle.setText("" + post.getTitle());
        holder.txtUrl.setText("" + post.getUrl());

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(ctx, holder.btnOption);
                        popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.optEdit:
                                        Intent intent = new Intent(ctx, UpdatePostActivity.class);
                                        intent.putExtra("id", post.getId());
                                        intent.putExtra("title", post.getTitle());
                                        intent.putExtra("url", post.getUrl());

                                        ctx.startActivity(intent);
                                        break;
                                    case R.id.optDelete:
                                        new AlertDialog.Builder(ctx)
                                                .setTitle("Warning")
                                                .setMessage("Are you sure you want to delete this permanently?")
                                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts");
                                                        ref.child(post.getId() + "").removeValue();
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                        break;
                                    case R.id.optCreateShortcut:
                                        createShortcut(post.getTitle(), post.getUrl());
                                        break;
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (post.getUrl().contains("https://m.youtube.com")) {
                    String url = "https://www.youtube.com/watch?v=" + post.getUrl().split("v=")[1];
                    try {
//                        ytEx.extract(url, true, true);
                    } catch (Exception exp) {
                        Toast.makeText(ctx, "Already on downloading list..!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ctx, "" + exp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    ctx.startActivity(new Intent(ctx, WebViewActivity.class).putExtra("url", post.getUrl() + ""));
            }
        });
    }

    private void downloadVideo(String url) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("" + videoTitle);
        request.setDescription("downloading");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String videoName = videoTitle + ".mp4";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, videoName);

        DownloadManager manager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private void createShortcut(String title, String url) {

        // create shortcut if requested
        Intent shortcutIntent = new Intent(ctx.getApplicationContext(), WebViewActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("url", url);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        Intent addIntent = new Intent();
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(ctx.getApplicationContext(), R.drawable.ic_launcher));
//        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        addIntent.putExtra("duplicate", false);
//        addIntent.setAction(Intent.ACTION_MAIN);
//        ctx.getApplicationContext().sendBroadcast(addIntent);
//
//        Log.d("inside PostAdapter", "createShortcut: ");

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(ctx.getApplicationContext())) {
            ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(ctx.getApplicationContext(), "#1")
//                    .setIntent(new Intent(ctx.getApplicationContext(), MainActivity.class).setAction(Intent.ACTION_MAIN))
                    .setIntent(shortcutIntent) // !!! intent's action must be set on oreo
                    .setShortLabel(title)
                    .setIcon(IconCompat.createWithResource(ctx.getApplicationContext(), R.drawable.logo))
                    .build();
            ShortcutManagerCompat.requestPinShortcut(ctx.getApplicationContext(), shortcutInfo, null);
        } else {
            Toast.makeText(ctx, "launcher does not support short cut icon", Toast.LENGTH_LONG).show();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtUrl;
        LinearLayout btnOption;

        ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
