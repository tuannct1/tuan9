package com.hvacpro.faultcodes.tuan9.bai2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai2.entity.HanTu
import com.hvacpro.faultcodes.tuan9.bai2.entity.splitEntries
import com.hvacpro.faultcodes.tuan9.databinding.FragmentHanTuBinding

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [HanTuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HanTuFragment : Fragment() {
    private lateinit var binding : FragmentHanTuBinding
    private var param1: HanTu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = BundleCompat.getParcelable(it, ARG_PARAM1, HanTu::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHanTuBinding.inflate(inflater, container, false)
        binding.tvHan.text = param1?.han ?: ""
        binding.tvMean.text = splitEntries(param1?.mean ?: "")
        binding.tvPinyin.text = param1?.pinyin ?: ""
        binding.tvViet.text = param1?.viet ?: ""
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HanTuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(hanTu: HanTu?) =
            HanTuFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, hanTu)
                }
            }
    }
}