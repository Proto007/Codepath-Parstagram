package com.codepath.sadab.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.sadab.parstagram.MainActivity
import com.codepath.sadab.parstagram.Post
import com.codepath.sadab.parstagram.PostAdapter
import com.codepath.sadab.parstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView:RecyclerView
    lateinit var adapter:PostAdapter
    var allPosts:MutableList<Post> = mutableListOf()
    lateinit var swipeContainer: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView=view.findViewById(R.id.postRecyclerView)
        adapter= PostAdapter(requireContext(),allPosts)
        swipeContainer=view.findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            Log.i(TAG,"refreshing feed")
            queryPosts()
        }
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        postsRecyclerView.adapter=adapter
        postsRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        queryPosts()
    }

    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.findInBackground(object: FindCallback<Post> {
            override fun done(posts:MutableList<Post>?, e: ParseException?){
                if(e!=null){
                    Log.e(TAG,"Error fetching posts")
                    Log.e(TAG,e.localizedMessage)
                }else{
                    if(posts!=null){
                        adapter.clear()
                        for(post in posts){
                            Log.i(TAG,"Post: "+post.getDescription()+post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        swipeContainer.setRefreshing(false)
                    }
                }
            }
        })
    }
    companion object{
        const val TAG="FeedFragment"
    }
}