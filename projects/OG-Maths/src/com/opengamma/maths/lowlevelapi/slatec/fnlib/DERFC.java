/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.slatec.fnlib;

import com.opengamma.analytics.math.statistics.distribution.fnlib.DCSEVL;
import com.opengamma.analytics.math.statistics.distribution.fnlib.INITDS;
import com.opengamma.maths.commonapi.exceptions.MathsExceptionUnderflow;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.ogblas.auxiliary.D1MACH;

/**
 * DERFC(X) provides the ability to calculate the complementary error function at position 'x'. 
 * It does this using a set of Chebychev approximations for various parts of the function.
 * This code is an approximate translation of the equivalent function in the "Public Domain" code from SLATEC, see:
 * See http://www.netlib.org/slatec/fnlib/derfc.f
 */
public class DERFC {

  private static final double[] s_erfcs = {
    -0.49046121234691808039984544033376e-1,
    -0.14226120510371364237824741899631e+0,
    +0.10035582187599795575754676712933e-1,
    -0.57687646997674847650827025509167e-3,
    +0.27419931252196061034422160791471e-4,
    -0.11043175507344507604135381295905e-5,
    +0.38488755420345036949961311498174e-7,
    -0.11808582533875466969631751801581e-8,
    +0.32334215826050909646402930953354e-10,
    -0.79910159470045487581607374708595e-12,
    +0.17990725113961455611967245486634e-13,
    -0.37186354878186926382316828209493e-15,
    +0.71035990037142529711689908394666e-17,
    -0.12612455119155225832495424853333e-18,
    +0.20916406941769294369170500266666e-20,
    -0.32539731029314072982364160000000e-22,
    +0.47668672097976748332373333333333e-24,
    -0.65980120782851343155199999999999e-26,
    +0.86550114699637626197333333333333e-28,
    -0.10788925177498064213333333333333e-29,
    +0.12811883993017002666666666666666e-31
  };

  private static final double[] s_erc2cs = {
    -0.6960134660230950112739150826197e-1,
    -0.4110133936262089348982212084666e-1,
    +0.3914495866689626881561143705244e-2,
    -0.4906395650548979161280935450774e-3,
    +0.7157479001377036380760894141825e-4,
    -0.1153071634131232833808232847912e-4,
    +0.1994670590201997635052314867709e-5,
    -0.3642666471599222873936118430711e-6,
    +0.6944372610005012589931277214633e-7,
    -0.1371220902104366019534605141210e-7,
    +0.2788389661007137131963860348087e-8,
    -0.5814164724331161551864791050316e-9,
    +0.1238920491752753181180168817950e-9,
    -0.2690639145306743432390424937889e-10,
    +0.5942614350847910982444709683840e-11,
    -0.1332386735758119579287754420570e-11,
    +0.3028046806177132017173697243304e-12,
    -0.6966648814941032588795867588954e-13,
    +0.1620854541053922969812893227628e-13,
    -0.3809934465250491999876913057729e-14,
    +0.9040487815978831149368971012975e-15,
    -0.2164006195089607347809812047003e-15,
    +0.5222102233995854984607980244172e-16,
    -0.1269729602364555336372415527780e-16,
    +0.3109145504276197583836227412951e-17,
    -0.7663762920320385524009566714811e-18,
    +0.1900819251362745202536929733290e-18,
    -0.4742207279069039545225655999965e-19,
    +0.1189649200076528382880683078451e-19,
    -0.3000035590325780256845271313066e-20,
    +0.7602993453043246173019385277098e-21,
    -0.1935909447606872881569811049130e-21,
    +0.4951399124773337881000042386773e-22,
    -0.1271807481336371879608621989888e-22,
    +0.3280049600469513043315841652053e-23,
    -0.8492320176822896568924792422399e-24,
    +0.2206917892807560223519879987199e-24,
    -0.5755617245696528498312819507199e-25,
    +0.1506191533639234250354144051199e-25,
    -0.3954502959018796953104285695999e-26,
    +0.1041529704151500979984645051733e-26,
    -0.2751487795278765079450178901333e-27,
    +0.7290058205497557408997703680000e-28,
    -0.1936939645915947804077501098666e-28,
    +0.5160357112051487298370054826666e-29,
    -0.1378419322193094099389644800000e-29,
    +0.3691326793107069042251093333333e-30,
    -0.9909389590624365420653226666666e-31,
    +0.2666491705195388413323946666666e-31
  };

  private static final double[] s_erfccs = {
    +0.715179310202924774503697709496e-1,
    -0.265324343376067157558893386681e-1,
    +0.171115397792085588332699194606e-2,
    -0.163751663458517884163746404749e-3,
    +0.198712935005520364995974806758e-4,
    -0.284371241276655508750175183152e-5,
    +0.460616130896313036969379968464e-6,
    -0.822775302587920842057766536366e-7,
    +0.159214187277090112989358340826e-7,
    -0.329507136225284321486631665072e-8,
    +0.722343976040055546581261153890e-9,
    -0.166485581339872959344695966886e-9,
    +0.401039258823766482077671768814e-10,
    -0.100481621442573113272170176283e-10,
    +0.260827591330033380859341009439e-11,
    -0.699111056040402486557697812476e-12,
    +0.192949233326170708624205749803e-12,
    -0.547013118875433106490125085271e-13,
    +0.158966330976269744839084032762e-13,
    -0.472689398019755483920369584290e-14,
    +0.143587337678498478672873997840e-14,
    -0.444951056181735839417250062829e-15,
    +0.140481088476823343737305537466e-15,
    -0.451381838776421089625963281623e-16,
    +0.147452154104513307787018713262e-16,
    -0.489262140694577615436841552532e-17,
    +0.164761214141064673895301522827e-17,
    -0.562681717632940809299928521323e-18,
    +0.194744338223207851429197867821e-18,
    -0.682630564294842072956664144723e-19,
    +0.242198888729864924018301125438e-19,
    -0.869341413350307042563800861857e-20,
    +0.315518034622808557122363401262e-20,
    -0.115737232404960874261239486742e-20,
    +0.428894716160565394623737097442e-21,
    -0.160503074205761685005737770964e-21,
    +0.606329875745380264495069923027e-22,
    -0.231140425169795849098840801367e-22,
    +0.888877854066188552554702955697e-23,
    -0.344726057665137652230718495566e-23,
    +0.134786546020696506827582774181e-23,
    -0.531179407112502173645873201807e-24,
    +0.210934105861978316828954734537e-24,
    -0.843836558792378911598133256738e-25,
    +0.339998252494520890627359576337e-25,
    -0.137945238807324209002238377110e-25,
    +0.563449031183325261513392634811e-26,
    -0.231649043447706544823427752700e-26,
    +0.958446284460181015263158381226e-27,
    -0.399072288033010972624224850193e-27,
    +0.167212922594447736017228709669e-27,
    -0.704599152276601385638803782587e-28,
    +0.297976840286420635412357989444e-28,
    -0.126252246646061929722422632994e-28,
    +0.539543870454248793985299653154e-29,
    -0.238099288253145918675346190062e-29,
    +0.109905283010276157359726683750e-29,
    -0.486771374164496572732518677435e-30,
    +0.152587726411035756763200828211e-30
  };

  private static final double SQRTPI = 1.77245385090551602729816748334115;
  private static double s_eta;
  private static int s_nterf;
  private static int s_nterfc;
  private static int s_nterc2;
  private static double s_xsml;
  private static double s_txmax;
  private static double s_xmax;
  private static double s_sqeps;
  static {
    s_eta = 0.1 * D1MACH.three(); // slight variation from F77 SLATEC, comparing using doubles opposed to floats
    s_nterf = INITDS.initds(s_erfcs, 21, s_eta);
    s_nterfc = INITDS.initds(s_erfccs, 59, s_eta);
    s_nterc2 = INITDS.initds(s_erc2cs, 49, s_eta);
    s_xsml = -Math.sqrt(-Math.log(SQRTPI * D1MACH.three()));
    s_txmax = Math.sqrt(-Math.log(SQRTPI * D1MACH.one()));
    s_xmax = s_txmax - 0.5d * Math.log(s_txmax) / s_txmax - 0.01d;
    s_sqeps = Math.sqrt(2d * D1MACH.three());
  }

  /**
   * Gets the complimentary error function at position 'x'
   * @param x the position at which to evaluate the complimentary error function
   * @return the complimentary error function value at position 'x'
   */
  public static double derfc(final double x) {
    double ret = 0;
    if (x <= s_xsml) {
      return 2.d;
    }
    if (x > s_xmax) {
      throw new MathsExceptionUnderflow("x is so large that ERFC underflows");
    }
    double y = Math.abs(x);
    if (y <= 1d) {
      if (y < s_sqeps) {
        return (1d - 2d * x / SQRTPI);
      }
      if (y >= s_sqeps) {
        return (1d - x * (1d + DCSEVL.dcsevl(2.d * x * x - 1.d, s_erfcs, s_nterf)));
      }
    }
    y = y * y;
    if (y <= 4d) {
      ret = Math.exp(-y) / Math.abs(x) * (0.5d + DCSEVL.dcsevl((8.d / y - 5.d) / 3.d, s_erc2cs, s_nterc2));
    }
    if (y > 4d) {
      ret = Math.exp(-y) / Math.abs(x) * (0.5d + DCSEVL.dcsevl((8.d / y - 1.d), s_erfccs, s_nterfc));
    }
    if (x < 0d) {
      ret = 2d - ret;
    }
    return ret;
  }
}
