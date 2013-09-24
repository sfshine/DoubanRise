package com.su.doubanrise;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.websitebiz.ReviewService;
import com.su.doubanrise.widget.AsyncImageLoader;

/**
 * 查看评论
 * 
 * @author chenyc
 * 
 */
public class ReviewViewActivity extends BaseActivity {

	private TextView txtReviewTitle;
	private TextView txtReviewContent;
	private TextView txtReviewComment;
	private ImageView userImageView;
	private TextView txtSubjectTitle;
	private TextView txtUserInfo;

	private RatingBar ratingBar;
	Review review;

	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_review_view);
		Bundle extras = getIntent().getExtras();
		review = extras != null ? (Review) extras.getSerializable("review")
				: null;
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void findViewById() {
		txtReviewTitle = (TextView) findViewById(R.id.review_title);
		txtReviewContent = (TextView) findViewById(R.id.review_content);
		txtReviewComment = (TextView) findViewById(R.id.review_comments);
		userImageView = (ImageView) findViewById(R.id.user_img);
		txtSubjectTitle = (TextView) findViewById(R.id.subject_title);
		txtUserInfo = (TextView) findViewById(R.id.user_info);
		ratingBar = (RatingBar) findViewById(R.id.ratingbar);
		ratingBar.setVisibility(View.INVISIBLE);
		common_title_menu_button.setVisibility(View.INVISIBLE);
		setTitle("详细评论");
	}

	@Override
	protected void processLogic() {
		if (review == null) {
			return;
		}
		new AsyncTask<Review, Void, Review>() {

			private Spanned content;
			private Spanned comment;

			@Override
			protected Review doInBackground(Review... args) {
				Review review = args[0];
				try {
					review = ReviewService.getReviewContentAndComments(review);
					content = Html.fromHtml(review.getContent());
					comment = Html.fromHtml(review.getComments());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return review;
			}

			@Override
			protected void onPostExecute(Review review) {
				closeProgressBar();
				super.onPostExecute(review);
				txtReviewTitle.setText(review.getTitle());
				txtReviewContent.setText(content);
				txtReviewComment.setText(comment);
				AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
						ReviewViewActivity.this);
				asyncImageLoader.setAsyDrawableFromurl(
						review.getAuthorImageUrl(), userImageView);
				txtUserInfo.setText(" 评论人：" + review.getCreator());
				txtSubjectTitle
						.setText("《" + review.getSubjecttitle() + "》的评论");
				ratingBar.setRating(review.getRating());
				ratingBar.setVisibility(View.VISIBLE);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgressBar();
			}

		}.execute(review);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
