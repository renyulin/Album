package com.ryl.choosephoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageItemKeeper {

    private static ImageItemKeeper instance;
    public final static int INDEX_ALL = 0;
    public final static int INDEX_SINGLE = 1;
    private int maxCount = 9;
    private int imageType;

    private ImageItemKeeper() {
    }

    public static ImageItemKeeper getInstance() {
        if (instance == null) {
            instance = new ImageItemKeeper();
        }
        return instance;
    }

    private List<ImageItem> allSelectList = new ArrayList<ImageItem>();
    private List<ImageItem> singleSelectList = new ArrayList<ImageItem>();

    private List<ImageItem> currentList;

    private List<ImageItem> getSelectedImage(int index) {
        switch (index) {
            case INDEX_ALL:
                return allSelectList;
            case INDEX_SINGLE:
                return singleSelectList;
        }
        return null;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void switchWorkList(int index, int maxCount) {
        currentList = getSelectedImage(index);
        imageType = index;
        this.maxCount = maxCount;
    }

    public void switchWorkList(int index) {
        currentList = getSelectedImage(index);
        imageType = index;
    }

    public int getImagetType() {
        return imageType;
    }

    public List<ImageItem> getWorkingList() {
        return currentList;
    }

    public List<ImageItem> getWorkList(int index) {
        currentList = getSelectedImage(index);
        return currentList;
    }

    private Context context;

    public boolean contains(ImageItem item, Context context) {
        this.context = context;
        return currentList != null && currentList.contains(item);
    }

    public boolean remove(ImageItem item) {
        boolean result = false;
        if (currentList != null) {
            result = currentList.remove(item);
            setDefaultIndex();
        }
        return result;
    }

    public boolean add(ImageItem item) {
        if (currentList != null) {
            if (currentList.size() == 0) {
                item.isIndex = true;
                currentList.add(item);
                return true;
            }
            if (getImagetType() == INDEX_SINGLE) {
                currentList.clear();
                currentList.add(item);
                item.isIndex = true;
                return true;
            } else if (getImagetType() == INDEX_ALL && currentList.size() >= getMaxCount()) {
                Toast.makeText(context, "Nine pictures are selected at most", Toast.LENGTH_LONG).show();
                return false;
            }
            if (item.isIndex && !currentList.contains(item)) {
                for (ImageItem i : currentList) {
                    i.isIndex = false;
                }
                currentList.add(item);
                return true;
            }
            if (!currentList.contains(item)) {
                currentList.add(item);
            }
            setDefaultIndex();
            return true;
        }
        return false;
    }

    public int size() {
        return currentList == null ? 0 : currentList.size();
    }

    public void remove(int index) {
        if (currentList != null && index < currentList.size()) {
            currentList.remove(index);
            setDefaultIndex();
        }
    }

    public void setIndex(int position) {
        if (currentList != null && position < currentList.size()) {
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                item.isIndex = position == i;
            }
        }
    }

    public int getIndexPosition() {
        int position = 0;
        if (currentList != null) {
            for (ImageItem i : currentList) {
                if (i.isIndex) {
                    return position;
                }
                position++;
            }
        }
        return position;
    }

    public ImageItem getIndexItem() {
        if (currentList != null) {
            for (ImageItem i : currentList) {
                if (i.isIndex) {
                    return i;
                }
            }
            if (currentList.size() > 0) {
                return currentList.get(0);
            }
        }
        return null;
    }

    public void setDefaultIndex() {
        int total = 0;
        int lastIndex = 0;
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                if (item.isIndex) {
                    total++;
                    lastIndex = i;
                }
            }
        }
        if (currentList.size() > 0 && total == 0) {
            currentList.get(lastIndex).isIndex = true;
        }
        if (total > 1) {
            currentList.get(lastIndex).isIndex = false;
        }
        if (total > 2) {
            setDefaultIndex();
        }
    }

    private int getLocalCount() {
        int count = 0;
        for (int i = 0; i < currentList.size(); i++) {
            if (!currentList.get(i).isNetFile()) {
                count++;
            }
        }
        return count;
    }

    public File[] generateCompressedFileArray(Context context) {
        if (currentList != null && currentList.size() > 0) {
            File[] files = new File[getLocalCount()];
            int count = 0;
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                if (item.isNetFile())
                    continue;
                if (TextUtils.isEmpty(item.uploadPath)) {
                    File file = new File(item.imagePath);
                    Bitmap bmp = BitmapTools.compressImageSize(file, 300);
                    String filePath = FileManager.saveImageFiles(context, bmp);
                    item.uploadPath = filePath;
                    files[count++] = new File(filePath);
                } else {
                    files[count++] = new File(item.uploadPath);
                }
            }
            return files;
        }
        return null;
    }

    public File[] getFileArray(Context context) {
        if (currentList != null && currentList.size() > 0) {
            File[] files = new File[getLocalCount()];
            int count = 0;
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                files[count++] = new File(item.imagePath);
            }
            return files;
        }
        return null;
    }

    public void clearSelected() {
        if (currentList != null && currentList.size() != 9)
            currentList.clear();
    }
}
