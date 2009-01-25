package pl.edu.pw.iem.modrzejm.amath;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

import java.util.HashMap;

public class aMath extends Activity {
	EditText etExpression;
	ListView lvValue;
	ExpressionsDbAdapter dbAdapter;
	CommandEvaluator cEvaluator;
	Evaluator ev;
	HashMap<String, Integer> knownCommands;
	String session = "default";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etExpression = (EditText) findViewById(R.id.etExpression);
		// etExpression.setOnClickListener(onClicExpressionListener);
		etExpression.setOnKeyListener(onKeyExpressionListener);

		lvValue = (ListView) findViewById(R.id.lvValues);
		lvValue.setOnItemClickListener(onItemClickValueListener);
		dbAdapter = new ExpressionsDbAdapter(this);
		dbAdapter.open();
		dbAdapter.deleteSession("default");
		fetchToList();
		ev = new Evaluator();
		cEvaluator = new CommandEvaluator(dbAdapter);
	}

	private void fetchToList() {
		Cursor cursor = dbAdapter.fetchSession(session);
		startManagingCursor(cursor);
		String[] from = new String[] { ExpressionsDbAdapter.KEY_EXPRESSION,
				ExpressionsDbAdapter.KEY_VALUE, ExpressionsDbAdapter.KEY_ROWID,
				ExpressionsDbAdapter.KEY_SAVED };
		int[] to = new int[] { R.id.teRowTextExpression, R.id.teRowTextValue,
				R.id.tvRowNumber, R.id.tvSession };
		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this,
				R.layout.values_row, cursor, from, to);
		lvValue.setAdapter(scAdapter);
	}

	public void evaluate() {
		String exp = etExpression.getText().toString().trim();
		if (!exp.matches("\\s+")) {
			exp = exp.replace("\n", ""); // striping new line
			
			if (cEvaluator.evaluateCommand(exp)) {
				String val = ev.eval(exp);
				dbAdapter.insert(exp, val, session);
			}

			etExpression.setText("");
			fetchToList();
			lvValue.setSelection(lvValue.getChildCount() - 1);
		}
	}

	

	// private OnClickListener onClicExpressionListener = new OnClickListener()
	// {
	// @Override
	// public void onClick(View v) {
	// evaluate();
	// }
	// };

	private OnKeyListener onKeyExpressionListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER
					&& event.getAction() == KeyEvent.ACTION_UP) {
				evaluate();
			}
			return false;
		}
	};

	private OnItemClickListener onItemClickValueListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {

			Cursor cursor = dbAdapter.fetch(id);
			etExpression.setText(cursor.getString(cursor
					.getColumnIndex(ExpressionsDbAdapter.KEY_EXPRESSION)));
		}

	};

}