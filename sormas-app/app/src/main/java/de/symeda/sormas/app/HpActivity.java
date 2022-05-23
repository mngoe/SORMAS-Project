package de.symeda.sormas.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.symeda.sormas.app.backend.report.AggregateReport;
import de.symeda.sormas.app.caze.list.CaseListActivity;
import de.symeda.sormas.app.event.list.EventListActivity;
import de.symeda.sormas.app.login.LoginActivity;
import de.symeda.sormas.app.report.ReportActivity;
import de.symeda.sormas.app.report.aggregate.AggregateReportsActivity;
import de.symeda.sormas.app.task.list.TaskListActivity;

public class HpActivity extends BaseLocalizedActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SORMAS_LBDS", "HP Siseac");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp);

        mTextView = (TextView) findViewById(R.id.text);
    }

    public void taskClick(View view) {
        Log.d("SORMAS_LBDS", "HP Siseac- Click Task");
        Intent intent = new Intent(view.getContext(), TaskListActivity.class);
        startActivity(intent);
    }

    public void caseClick(View view) {
        Log.d("SORMAS_LBDS", "HP Siseac- Click Task");
        Intent intent = new Intent(view.getContext(), CaseListActivity.class);
        startActivity(intent);
    }

    public void mSersClick(View view) {
        Log.d("SORMAS_LBDS", "HP Siseac- mSers Task");
        Intent intent = new Intent(view.getContext(), AggregateReportsActivity.class);
        startActivity(intent);
    }

    public void statsClick(View view) {
        Log.d("SORMAS_LBDS", "HP Siseac- Stats Task");
        Intent intent = new Intent(view.getContext(), ReportActivity.class);
        startActivity(intent);
    }

    public void eventsClick(View view) {
        Log.d("SORMAS_LBDS", "HP Siseac- Events Task");
        Intent intent = new Intent(view.getContext(), EventListActivity.class);
        startActivity(intent);
    }

}