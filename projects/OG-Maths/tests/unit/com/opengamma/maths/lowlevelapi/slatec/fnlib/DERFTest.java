/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.slatec.fnlib;

import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import com.opengamma.maths.lowlevelapi.linearalgebra.blas.ogblas.auxiliary.D1MACH;


/**
 * Tests DERF
 */
public class DERFTest {

  static double[] input = {
      -8.0000, -7.9500, -7.9000, -7.8500, -7.8000, -7.7500, -7.7000, -7.6500, -7.6000, -7.5500, -7.5000, -7.4500, -7.4000, -7.3500, -7.3000, -7.2500, -7.2000, -7.1500, -7.1000, -7.0500, -7.0000,
      -6.9500, -6.9000, -6.8500, -6.8000, -6.7500, -6.7000, -6.6500, -6.6000, -6.5500, -6.5000, -6.4500, -6.4000, -6.3500, -6.3000, -6.2500, -6.2000, -6.1500, -6.1000, -6.0500, -6.0000, -5.9500,
      -5.9000, -5.8500, -5.8000, -5.7500, -5.7000, -5.6500, -5.6000, -5.5500, -5.5000, -5.4500, -5.4000, -5.3500, -5.3000, -5.2500, -5.2000, -5.1500, -5.1000, -5.0500, -5.0000, -4.9500, -4.9000,
      -4.8500, -4.8000, -4.7500, -4.7000, -4.6500, -4.6000, -4.5500, -4.5000, -4.4500, -4.4000, -4.3500, -4.3000, -4.2500, -4.2000, -4.1500, -4.1000, -4.0500, -4.0000, -3.9500, -3.9000, -3.8500,
      -3.8000, -3.7500, -3.7000, -3.6500, -3.6000, -3.5500, -3.5000, -3.4500, -3.4000, -3.3500, -3.3000, -3.2500, -3.2000, -3.1500, -3.1000, -3.0500, -3.0000, -2.9500, -2.9000, -2.8500, -2.8000,
      -2.7500, -2.7000, -2.6500, -2.6000, -2.5500, -2.5000, -2.4500, -2.4000, -2.3500, -2.3000, -2.2500, -2.2000, -2.1500, -2.1000, -2.0500, -2.0000, -1.9500, -1.9000, -1.8500, -1.8000, -1.7500,
      -1.7000, -1.6500, -1.6000, -1.5500, -1.5000, -1.4500, -1.4000, -1.3500, -1.3000, -1.2500, -1.2000, -1.1500, -1.1000, -1.0500, -1.0000, -0.9500, -0.9000, -0.8500, -0.8000, -0.7500, -0.7000,
      -0.6500, -0.6000, -0.5500, -0.5000, -0.4500, -0.4000, -0.3500, -0.3000, -0.2500, -0.2000, -0.1500, -0.1000, -0.0500, 0.0000, 0.0500, 0.1000, 0.1500, 0.2000, 0.2500, 0.3000, 0.3500, 0.4000,
      0.4500, 0.5000, 0.5500, 0.6000, 0.6500, 0.7000, 0.7500, 0.8000, 0.8500, 0.9000, 0.9500, 1.0000, 1.0500, 1.1000, 1.1500, 1.2000, 1.2500, 1.3000, 1.3500, 1.4000, 1.4500, 1.5000, 1.5500, 1.6000,
      1.6500, 1.7000, 1.7500, 1.8000, 1.8500, 1.9000, 1.9500, 2.0000, 2.0500, 2.1000, 2.1500, 2.2000, 2.2500, 2.3000, 2.3500, 2.4000, 2.4500, 2.5000, 2.5500, 2.6000, 2.6500, 2.7000, 2.7500, 2.8000,
      2.8500, 2.9000, 2.9500, 3.0000, 3.0500, 3.1000, 3.1500, 3.2000, 3.2500, 3.3000, 3.3500, 3.4000, 3.4500, 3.5000, 3.5500, 3.6000, 3.6500, 3.7000, 3.7500, 3.8000, 3.8500, 3.9000, 3.9500, 4.0000,
      4.0500, 4.1000, 4.1500, 4.2000, 4.2500, 4.3000, 4.3500, 4.4000, 4.4500, 4.5000, 4.5500, 4.6000, 4.6500, 4.7000, 4.7500, 4.8000, 4.8500, 4.9000, 4.9500, 5.0000, 5.0500, 5.1000, 5.1500, 5.2000,
      5.2500, 5.3000, 5.3500, 5.4000, 5.4500, 5.5000, 5.5500, 5.6000, 5.6500, 5.7000, 5.7500, 5.8000, 5.8500, 5.9000, 5.9500, 6.0000, 6.0500, 6.1000, 6.1500, 6.2000, 6.2500, 6.3000, 6.3500, 6.4000,
      6.4500, 6.5000, 6.5500, 6.6000, 6.6500, 6.7000, 6.7500, 6.8000, 6.8500, 6.9000, 6.9500, 7.0000, 7.0500, 7.1000, 7.1500, 7.2000, 7.2500, 7.3000, 7.3500, 7.4000, 7.4500, 7.5000, 7.5500, 7.6000,
      7.6500, 7.7000, 7.7500, 7.8000, 7.8500, 7.9000, 7.9500, 8.0000
  };

  static double[] answer = {
      -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000,
      -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000,
      -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000,
      -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000,
      -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -1.0000000000000000, -0.9999999999999999, -0.9999999999999999, -0.9999999999999998,
      -0.9999999999999996, -0.9999999999999992, -0.9999999999999987, -0.9999999999999977, -0.9999999999999958, -0.9999999999999927, -0.9999999999999871, -0.9999999999999777, -0.9999999999999615,
      -0.9999999999999338, -0.9999999999998869, -0.9999999999998075, -0.9999999999996740, -0.9999999999994507, -0.9999999999990787, -0.9999999999984626, -0.9999999999974469, -0.9999999999957810,
      -0.9999999999930624, -0.9999999999886479, -0.9999999999815149, -0.9999999999700474, -0.9999999999517030, -0.9999999999225040, -0.9999999998762595, -0.9999999998033839, -0.9999999996891137,
      -0.9999999995108290, -0.9999999992340556, -0.9999999988065282, -0.9999999981494259, -0.9999999971445058, -0.9999999956153229, -0.9999999932999724, -0.9999999898117551, -0.9999999845827421,
      -0.9999999767832678, -0.9999999652077514, -0.9999999481137066, -0.9999999229960725, -0.9999998862727434, -0.9999998328489421, -0.9999997555173494, -0.9999996441370070, -0.9999994845161754,
      -0.9999992569016276, -0.9999989339482065, -0.9999984780066371, -0.9999978375231799, -0.9999969422902035, -0.9999956972205363, -0.9999939742388483, -0.9999916017886847, -0.9999883513426328,
      -0.9999839201742399, -0.9999779095030014, -0.9999697969579359, -0.9999589021219005, -0.9999443437200386, -0.9999249868053346, -0.9998993780778803, -0.9998656672600594, -0.9998215122479760,
      -0.9997639655834707, -0.9996893396573608, -0.9995930479825550, -0.9994694198877490, -0.9993114861033550, -0.9991107329678676, -0.9988568234026434, -0.9985372834133188, -0.9981371537020181,
      -0.9976386070373253, -0.9970205333436670, -0.9962580960444569, -0.9953222650189527, -0.9941793335921891, -0.9927904292352574, -0.9911110300560857, -0.9890905016357308, -0.9866716712191824,
      -0.9837904585907745, -0.9803755850233603, -0.9763483833446440, -0.9716227332620125, -0.9661051464753108, -0.9596950256374591, -0.9522851197626487, -0.9437621961227241, -0.9340079449406524,
      -0.9229001282564582, -0.9103139782296352, -0.8961238429369148, -0.8802050695740816, -0.8624361060900967, -0.8427007929497149, -0.8208908072732776, -0.7969082124228318, -0.7706680576083523,
      -0.7421009647076604, -0.7111556336535152, -0.6778011938374180, -0.6420293273556714, -0.6038560908479257, -0.5633233663251088, -0.5204998778130465, -0.4754817197869230, -0.4283923550466679,
      -0.3793820535623100, -0.3286267594591272, -0.2763263901682370, -0.2227025892104777, -0.1679959714273629, -0.1124629160182845, -0.0563719777970164, 0.0000000000000000, 0.0563719777970164,
      0.1124629160182845, 0.1679959714273629, 0.2227025892104777, 0.2763263901682370, 0.3286267594591272, 0.3793820535623100, 0.4283923550466679, 0.4754817197869230, 0.5204998778130465,
      0.5633233663251088, 0.6038560908479257, 0.6420293273556714, 0.6778011938374180, 0.7111556336535152, 0.7421009647076604, 0.7706680576083523, 0.7969082124228318, 0.8208908072732776,
      0.8427007929497149, 0.8624361060900967, 0.8802050695740816, 0.8961238429369148, 0.9103139782296352, 0.9229001282564582, 0.9340079449406524, 0.9437621961227241, 0.9522851197626487,
      0.9596950256374591, 0.9661051464753108, 0.9716227332620125, 0.9763483833446440, 0.9803755850233603, 0.9837904585907745, 0.9866716712191824, 0.9890905016357308, 0.9911110300560857,
      0.9927904292352574, 0.9941793335921891, 0.9953222650189527, 0.9962580960444569, 0.9970205333436670, 0.9976386070373253, 0.9981371537020181, 0.9985372834133188, 0.9988568234026434,
      0.9991107329678676, 0.9993114861033550, 0.9994694198877490, 0.9995930479825550, 0.9996893396573608, 0.9997639655834707, 0.9998215122479760, 0.9998656672600594, 0.9998993780778803,
      0.9999249868053346, 0.9999443437200386, 0.9999589021219005, 0.9999697969579359, 0.9999779095030014, 0.9999839201742399, 0.9999883513426328, 0.9999916017886847, 0.9999939742388483,
      0.9999956972205363, 0.9999969422902035, 0.9999978375231799, 0.9999984780066371, 0.9999989339482065, 0.9999992569016276, 0.9999994845161754, 0.9999996441370070, 0.9999997555173494,
      0.9999998328489421, 0.9999998862727434, 0.9999999229960725, 0.9999999481137066, 0.9999999652077514, 0.9999999767832678, 0.9999999845827421, 0.9999999898117551, 0.9999999932999724,
      0.9999999956153229, 0.9999999971445058, 0.9999999981494259, 0.9999999988065282, 0.9999999992340556, 0.9999999995108290, 0.9999999996891137, 0.9999999998033839, 0.9999999998762595,
      0.9999999999225040, 0.9999999999517030, 0.9999999999700474, 0.9999999999815149, 0.9999999999886479, 0.9999999999930624, 0.9999999999957810, 0.9999999999974469, 0.9999999999984626,
      0.9999999999990787, 0.9999999999994507, 0.9999999999996740, 0.9999999999998075, 0.9999999999998869, 0.9999999999999338, 0.9999999999999615, 0.9999999999999777, 0.9999999999999871,
      0.9999999999999927, 0.9999999999999958, 0.9999999999999977, 0.9999999999999987, 0.9999999999999992, 0.9999999999999996, 0.9999999999999998, 0.9999999999999999, 0.9999999999999999,
      1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000,
      1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000,
      1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000,
      1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000,
      1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000, 1.0000000000000000
  };

  @Test
  public void erfTest() {
    double ans;
    for (int i = 0; i < input.length; i++) {
      ans = DERF.derf(input[i]);
      assertTrue(Math.abs(answer[i] - ans) < 1e-14); // should get within this
    }
  }

  @Test
  void erfEdgeCasesTest() {
    // 1
    assertTrue(Math.abs(DERF.derf(1) - .842700792949715d) < 1e-15);
    // -1
    assertTrue(Math.abs(DERF.derf(-1) + .842700792949715d) < 1e-15);
    // 0.5(Machine precision/machine radix)
    assertTrue(Math.abs(DERF.derf(D1MACH.three() / 2) - 6.26376265908397e-17) < 1e-16);
  }

}
