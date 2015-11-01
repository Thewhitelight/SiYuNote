package cn.libery.siyunote.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.libery.siyunote.Constants;
import cn.libery.siyunote.R;

public class NoteListFragment extends Fragment {

    private static int NoteTypes;

    public static NoteListFragment newInstance(int NoteType) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        NoteTypes = NoteType;
        args.putInt(Constants.NOTES_TYPE, NoteType);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (NoteTypes) {
            case 0:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
