package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpEngineersList(MockData.engineers)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    private fun updateEngineersList(engineers: List<Engineer>) {
        val adapter = binding.list.adapter as? EngineersRecyclerViewAdapter
        adapter?.updateData(engineers)
    }

    private fun sortList(engineers: List<Engineer>, type: Int) {
        var sorted: List<Engineer> = engineers
        when (type) {
            0 -> {
                sorted = engineers.sortedWith(compareBy { it.quickStats.years })
            }
            1 -> {
                sorted =engineers.sortedWith(compareBy { it.quickStats.coffees })
            }
            2 -> {
                sorted = engineers.sortedWith(compareBy { it.quickStats.bugs })
            }
        }
        updateEngineersList(sorted)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> {
                sortList(MockData.engineers, 0)
                true
            }

            R.id.action_coffees -> {
                sortList(MockData.engineers, 1)
                true
            }

            R.id.action_bugs -> {
                sortList(MockData.engineers, 2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}