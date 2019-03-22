import com.github.nijian.jkeel.algorithms.serration.CalcConfig
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand

CalcConfig {

    cid '[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN, illustrationVersion:v1]'

    /**
     * in order to reach better performance, rate table is defined in array format
     */

    final
    float[] coiRate = [12.08, 7.33, 2.33, 1.84, 1.93, 1.63, 1.75, 1.50, 1.43, 1.37, 1.38, 1.25, 1.33, 1.23, 1.28, 1.21, 1.25, 1.20, 1.24, 1.19, 1.25, 1.18, 1.31, 1.20, 1.42, 1.23, 1.57, 1.27, 1.74, 1.32, 1.92, 1.37, 2.09, 1.42, 2.22, 1.46, 2.32, 1.50, 2.38, 1.53, 2.40, 1.56, 2.40, 1.58, 2.38, 1.60, 2.34, 1.62, 2.30, 1.65, 2.25, 1.67, 2.22, 1.70, 2.21, 1.74, 2.20, 1.78, 2.22, 1.82, 2.25, 1.87, 2.30, 1.92, 2.37, 1.97, 2.45, 2.04, 2.55, 2.11, 2.67, 2.20, 2.82, 2.32, 2.99, 2.46, 3.18, 2.63, 3.40, 2.82, 3.65, 3.03, 3.92, 3.25, 4.21, 3.48, 4.53, 3.70, 4.87, 3.94, 5.23, 4.18, 5.62, 4.42, 6.03, 4.69, 6.47, 4.98, 6.96, 5.29, 7.50, 5.63, 8.13, 6.00, 8.83, 6.42, 9.63, 6.88, 10.51, 7.35, 11.46, 7.83, 12.47, 8.30, 13.54, 8.75, 14.68, 9.20, 15.92, 9.70, 17.30, 10.30, 18.86, 11.04, 20.62, 11.99, 22.59, 13.13, 24.77, 14.42, 27.12, 15.79, 29.63, 17.21, 32.29, 18.63, 35.15, 20.09, 38.31, 21.73, 41.93, 24.08, 47.06, 27.04, 52.62, 30.35, 58.60, 34.06, 64.98, 38.25, 71.78, 43.04, 79.01, 48.56, 86.72, 54.96, 94.96, 62.40, 103.88, 70.21, 113.60, 79.03, 124.25, 88.92, 135.99, 100.00, 148.97, 112.38, 163.31, 126.20, 179.09, 141.62, 193.23, 155.88, 208.58, 171.47, 225.19, 188.46, 243.14, 206.95, 262.48, 227.02, 283.28, 248.75, 305.57, 272.19, 329.39, 297.40, 354.76, 324.42, 381.67, 353.25, 421.49, 414.38, 541.50, 537.74, 745.65, 744.46, 503.25, 503.25, 547.72, 547.72, 586.26, 586.26, 626.26, 626.26, 667.65, 667.65, 710.36, 710.36, 754.28, 754.28, 799.33, 799.33, 845.37, 845.37, 892.27, 892.27, 939.90, 939.90, 1000.00, 1000.00]

    final
    BigDecimal[] fsBonusRate = [0.0, 250000000.0, 0.0, 250000000.0, 500000000.0, 0.002, 500000000.0, 1000000000.0, 0.004, 1000000000.0, -1.0, 0.006]

    /**
     * Surrender charge percentage rate table
     */
    final
    BigDecimal[] scpRate = [1, 1, 0.9, 0.8, 0.7, 0.5, 0.25]

    /**
     * Initial charge percentage over Regular CP rate table
     */
    final
    BigDecimal[] icmRate = [0.5, 0.25, 0.2, 0.15, 0.1]

    /**
     * Initial charge percentage over Regular Topup rate table
     */
    final
    BigDecimal[] ictRate = [0.08, 0.06, 0.06, 0.06, 0.05]

    /**
     * Death Benefit Factor rate table
     */
    final
    BigDecimal[] dbFactorRate = [0.2, 0.4, 0.6, 0.8]

    /**
     * convert raw input (quotation instance) to simple format for simplifying formula
     */
    Convert { rawInput, var, i ->
        i.illusDate = new Date(119, 1, 1)
        i.insuredDOB = new Date(118, 0, 1)
        i.age = nearestAge(i.illusDate, i.insuredDOB) //int
        i.phDOB = new Date(94, 1, 7)
        i.regTopup = new BigDecimalOperand("1000000.00")
        i.regCP = new BigDecimalOperand("1000000.00")
        i.fqFactor = 12
        i.polTermOpt = 1
        i.premTermOpt = -4
        i.SI = new BigDecimalOperand("1800000000.00")
        i.SI_Inc_factor = new BigDecimalOperand('0.05')
        i.DBOpt = 2
        i.loadingRate = new BigDecimalOperand('0.0')
        i.gender = 1

//        i.count = 10
        if (i.polTermOpt == 1) {
            i.count = (90 - i.age) * 12
        } else if (i.polTermOpt == 2) {
            i.count = (95 - i.age) * 12
        } else if (i.polTermOpt == 3) {
            i.count = (100 - i.age) * 12
        } else {
            i.count = (105 - i.age) * 12
        }

        i.unitPriceMapH = var.unitPriceMapH
        i.unitPriceMapL = var.unitPriceMapL
        i.unitPriceMapG = var.unitPriceMapG

    }

    strategy "NCV001_OUTPUT", { i ->
        createGroupIndex 'Policy year', 12, 'Main_Prem_t'
        createGroupIndexOnBase 'Age', 12, nearestAge(i.illusDate, i.insuredDOB), 'Main_Prem_t'
        putGroupSum 'Total premiums paid in year', 12, 'Main_Prem_t', 'Topup_t'
        putGroupSum 'Initial charge', 12, 'Initial_charge_main_t', 'Initial_charge_topup_t'
        putGroupSum 'Allocated premium into Policy Account Value', 12, 'Alloc_main_t', 'Alloc_topup_t'
        putLGroupLast 'Death benefit(H)', 12, 'DB_eom_H_t'
        putLGroupLast 'Fund size bonus benefit(H)', 12, 'FS_bonus_H_t'
        putLGroupLast 'Policy Account Value(H)', 12, 'PAV_BB_H_t'
        putLGroupLast 'Surrender value(H)', 12, 'SV_H_t'
        putLGroupLast 'Death benefit(L)', 12, 'DB_eom_L_t'
        putLGroupLast 'Fund size bonus benefit(L)', 12, 'FS_bonus_L_t'
        putLGroupLast 'Policy Account Value(L)', 12, 'PAV_BB_L_t'
        putLGroupLast 'Surrender value(L)', 12, 'SV_L_t'
        putLGroupSum 'Cost of Insurance of the main policy(G)', 12, 'COI_G_t'
        putLGroupLast 'Death benefit(G)', 12, 'DB_eom_G_t'
        putLGroupLast 'Fund size bonus benefit(G)', 12, 'FS_bonus_G_t'
        putLGroupLast 'Policy Account Value(G)', 12, 'PAV_BB_G_t'
        putLGroupLast 'Surrender value(G)', 12, 'SV_G_t'
        putLLast 'PAV_AB_end(H)', 'PAV_AB_H_t'
        putLLast 'PAV_AB_end(L)', 'PAV_AB_L_t'
        putLLast 'PAV_AB_end(G)', 'PAV_AB_G_t'
        putLast 'LB_term_m', 'LB_term_m'
        putLLast 'LY_bonus_acc_H_end', 'LY_bonus_acc_H_t'
        putLLast 'LY_bonus_acc_L_end', 'LY_bonus_acc_L_t'
        putLLast 'LY_bonus_acc_G_end', 'LY_bonus_acc_G_t'
        putLLast 'FS_bonus_acc_H_end', 'FS_bonus_acc_H_t'
        putLLast 'FS_bonus_acc_L_end', 'FS_bonus_acc_L_t'
        putLLast 'FS_bonus_acc_G_end', 'FS_bonus_acc_G_t'

    }

    ItemGroupCount { i ->
        i.count
    }

    LayoutCount { i ->
        i.count
    }

    ///////////////////////////////////// Calculation Layout 1 //////////////////
    ///////////////// Parallel Area 1 /////////////////////
    /**
     * Age of the Insured at Illustration date
     */
    formula "Age_TI_t0", { i, int index ->
        i.age
    }

    formula "Age_PH_t0", { i, int index ->
        i.age
    }

    formula "Age_Spouse_t0", { i, int index ->
        i.age
    }
    /**
     * Annualized Committed Premium:
     * Ann_CP = Reg_CP * Fq_factor
     * With Fq_factor base on Prem_fq as follow:
     * Annual: 1
     * Semi-annual: 2
     * Quarterly: 4
     * Monthly: 12
     */
    formula "Ann_CP", { i, int index ->
        i.regCP * i.fqFactor
    }
    /**
     * Annualized Top-up premium
     */
    formula "Ann_topup", { i, int index ->
        i.regTopup * i.fqFactor
    }
    /**
     * Longevity benefit term in month:
     * If Maturity_opt = 90 then LB_term_m = 5 * 12 = 60
     * If Maturity_opt = 95/100 then LB_term_m = 12
     */
    formula "LB_term_m", { i, int index ->
        if (i.polTermOpt == 1) {
            60
        } else if (i.polTermOpt == 2 || i.polTermOpt == 3) {
            12
        } else {
            0
        }
    }
    ///////////////// Parallel Area 2 /////////////////////
    /**
     * Policy term:
     * If Pol_term_opt = Till age 90/95/100 years old then
     * Pol_term = 90/95/100 – Age_TI_t0
     * If Pol_term_opt = No then
     * Pol_term = 105 – Age_TI_t0
     */
    formula "Pol_term", { i, int index ->
        if (i.polTermOpt == 1) {
            90 - i.age
        } else if (i.polTermOpt == 2) {
            95 - i.age
        } else if (i.polTermOpt == 3) {
            100 - i.age
        } else {
            105 - i.age
        }
    }
    ///////////////// Parallel Area 3 /////////////////////
    /**
     * Premium term:
     * o If Prem_term_opt: Until age 55,60,65 then
     * Prem_term = 55/60/65 – Age_TI_t0
     * o If Prem_term_opt: 10/15/20 years then
     * Prem_term = 10/15/20
     * o If Prem_term_opt : Whole life then
     * Prem_term = Pol_term
     */
    formula "Prem_term", { i, int index ->
        if (i.premTermOpt == -1) {
            55 - i.age
        } else if (i.premTermOpt == -2) {
            60 - i.age
        } else if (i.premTermOpt == -3) {
            65 - i.age
        } else if (i.premTermOpt == -4) {
            get 'Pol_term'
        } else {
            i.premTermOpt
        }
    }
    ///////////////////////////////////// Calculation Layout 2 //////////////////
    ///////////////// Parallel Area 1 /////////////////////
    /**
     * Main Premium paid at begin of tth month:
     * o Main_Prem_t = Reg_CP if t <= 12 * Prem_term
     * o Main_Prem_t = 0 else
     */
    formula "Main_Prem_t", { i, int index ->
        if ((index + 1) <= (12 * get('Prem_term'))) {
            if (i.fqFactor == 1 && index % 12 == 0) {
                i.regCP
            } else if (i.fqFactor == 2 && index % 6 == 0) {
                i.regCP
            } else if (i.fqFactor == 4 && index % 4 == 0) {
                i.regCP
            } else if (i.fqFactor == 12) {
                i.regCP
            } else {
                0
            }
        } else {
            0
        }
    }
    /**
     * Topup paid at begin of tth month:
     * o Topup_t = Topup if t <= 12 * Prem_term
     * o Topup_t = 0 else
     */
    formula "Topup_t", { i, int index ->
        if ((index + 1) <= (12 * get('Prem_term'))) {
            if (i.fqFactor == 1 && index % 12 == 0) {
                i.regTopup
            } else if (i.fqFactor == 2 && index % 6 == 0) {
                i.regTopup
            } else if (i.fqFactor == 4 && index % 4 == 0) {
                i.regTopup
            } else if (i.fqFactor == 12) {
                i.regTopup
            } else {
                0
            }
        } else {
            0
        }
    }
    /**
     * Surrender charge at tth month:
     * SC_t = Surrender charge percentage * Ann_CP
     */
    formula "SC_t", { i, int index ->
        int yearIndex = index.intdiv(12) //from 0
        if (yearIndex < scpRate.length) {
            get('Ann_CP') * scpRate[yearIndex]
        } else {
            0
        }
    }
    /**
     * Increased Sum Insured at tth month:
     * Inc_SI_t = SI * ( 1+ (Pol_y_t  -  1) * SI_Inc_factor)
     */
    formula "Inc_SI_t", { i, int index ->
        int yearIndex = index.intdiv(12) //from 0
        i.SI * (1 + yearIndex * i.SI_Inc_factor)
    }
    /**
     * Price of unit of first day of tth month:
     * Price unit is set at 10.000 on Illustration date and calculate each day
     * o Interest rate : 7% per annual => 0.57% per month
     */
    formula "Price_BOM_H_t", { i, int index ->
        i.unitPriceMapH[dateStr(i.illusDate, index)]
    }

    /**
     * Price unit on last day of tth month
     */
    formula "Price_EOM_H_t", { i, int index ->
        i.unitPriceMapH[dateStr(i.illusDate, index, true)]
    }
    /**
     * Price of unit of first day of tth month:
     * Price unit is set at 10.000 on Illustration date and calculate each day
     * o Interest rate: 5% per annual => 0.41% per month
     */
    formula "Price_BOM_L_t", { i, int index ->
        i.unitPriceMapL[dateStr(i.illusDate, index)]
    }
    /**
     * Price unit on last day of tth month
     */
    formula "Price_EOM_L_t", { i, int index ->
        i.unitPriceMapL[dateStr(i.illusDate, index, true)]
    }
    /**
     * Price of unit of first day of tth month:
     * Price unit is set at 10.000 on Illustration date and calculate each day
     * o Gurantee rate
     */
    formula "Price_BOM_G_t", { i, int index ->
        i.unitPriceMapG[dateStr(i.illusDate, index)]
    }
    /**
     * Price unit on last day of tth month
     */
    formula "Price_EOM_G_t", { i, int index ->
        i.unitPriceMapG[dateStr(i.illusDate, index, true)]
    }
    ///////////////// Parallel Area 2 /////////////////////
    /**
     * Initial charge over main premium paid at begin of tth month:
     * Initial_charge_main_t = Initial charge percentage over Regular CP* Main_prem_t
     */
    formula "Initial_charge_main_t", { i, int index ->
        int yearIndex = index.intdiv(12) //from 0
        def rate = 0.025
        if (yearIndex < icmRate.length) {
            rate = icmRate[yearIndex]
        }
        getx(index, 'Main_Prem_t') * rate
    }
    /**
     * Initial charge over topup paid at begin of tth month:
     * Initial_charge_topup_t = Initial charge percentage over topup * Topup_t
     */
    formula "Initial_charge_topup_t", { i, int index ->
        int yearIndex = index.intdiv(12) //from 0
        def rate = 0.025
        if (yearIndex < ictRate.length) {
            rate = ictRate[yearIndex]
        }
        getx(index, 'Topup_t') * rate
    }
    ///////////////// Parallel Area 3 /////////////////////
    /**
     * Allocated main premium at begin of tth month:
     * Alloc_main_t = Main_prem_t  - Initial_charge_main_t
     */
    formula "Alloc_main_t", { i, int index ->
        getx(index, 'Main_Prem_t') - getx(index, 'Initial_charge_main_t')
    }
    /**
     * Allocated topup at begin of tth month:
     * Alloc_topup_t =  Topup_t – Initial_change_topup_t
     */
    formula "Alloc_topup_t", { i, int index ->
        getx(index, 'Topup_t') - getx(index, 'Initial_charge_topup_t')
    }
    ///////////////// Parallel Area 4 /////////////////////
    /**
     * Allocated unit from premium allocated at begin of tth month:
     * =(Alloc_main_t + Alloc_topup_t)/Price_BOM
     */
    formula "Allocated_unit_H_t", { i, int index ->
        (getx(index, 'Alloc_main_t') + getx(index, 'Alloc_topup_t')) / getx(index, 'Price_BOM_H_t')
    }
    /**
     * Allocated unit from premium allocated at begin of tth month:
     * =(Alloc_main_t + Alloc_topup_t)/Price_BOM
     */
    formula "Allocated_unit_L_t", { i, int index ->
        (getx(index, 'Alloc_main_t') + getx(index, 'Alloc_topup_t')) / getx(index, 'Price_BOM_L_t')
    }
    /**
     * Allocated unit from premium allocated at begin of tth month
     * =(Alloc_main_t + Alloc_topup_t)/Price_BOM
     */
    formula "Allocated_unit_G_t", { i, int index ->
        (getx(index, 'Alloc_main_t') + getx(index, 'Alloc_topup_t')) / getx(index, 'Price_BOM_G_t')
    }
    /**
     * Allocated topup unit at tth month:
     * = Alloc_topup_t / Price_BOM
     */
    formula "Alloc_topup_unit_H_t", { i, int index ->
        getx(index, 'Alloc_topup_t') / getx(index, 'Price_BOM_H_t')
    }
    /**
     * Allocated topup unit at tth month:
     * = Alloc_topup_t / Price_BOM
     */
    formula "Alloc_topup_unit_L_t", { i, int index ->
        getx(index, 'Alloc_topup_t') / getx(index, 'Price_BOM_L_t')
    }
    /**
     * Allocated topup unit at tth month
     * = Alloc_topup_t / Price_BOM
     */
    formula "Alloc_topup_unit_G_t", { i, int index ->
        getx(index, 'Alloc_topup_t') / getx(index, 'Price_BOM_G_t')
    }
    ///////////////////////////////////// Calculation Layout 3 //////////////////
    ///////////////// Parallel Area 1 /////////////////////
    /**
     * Total main premium paid from issue until tth month
     * (includes payment at begin of tth month)
     * Total_main_premium_paid_t = Sum of (Main_prem_i) with i from 1 to t
     */
    formula "Total_main_premium_paid_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Main_Prem_t')
        } else {
            getLx(layoutIndex - 1, 'Total_main_premium_paid_t') + getx(layoutIndex, 'Main_Prem_t')
        }
    }
    /**
     * Total Premium paid from issue until tth month
     * (includes both main policy premium and top up and includes payments at begin of tth month)
     * Total_premium_paid_t = Sum of (Main_prem_i and topup_i) with i from 1 to t
     */
    formula "Total_premium_paid_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Main_Prem_t') + getx(layoutIndex, 'Topup_t')
        } else {
            getLx(layoutIndex - 1, 'Total_premium_paid_t') + getx(layoutIndex, 'Main_Prem_t') + getx(layoutIndex, 'Topup_t')
        }
    }
    ///////////////// Parallel Area 2 /////////////////////
    /**
     * Policy account value unit before deduce without bonus unit begin of tth month
     * = PAV_unit_AD_no_bonus_t-1 + Allocated_unit_t
     */
    formula "PAV_unit_BD_no_bonus_H_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Allocated_unit_H_t')
        } else {
            getLx(layoutIndex - 1, 'PAV_unit_AD_no_bonus_H_t') + getx(layoutIndex, 'Allocated_unit_H_t')
        }
    }
    /**
     * Policy account value unit before deduce without bonus unit begin of tth month
     * = PAV_unit_AD_no_bonus_t-1 + Allocated_unit_t
     */
    formula "PAV_unit_BD_no_bonus_L_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Allocated_unit_L_t')
        } else {
            getLx(layoutIndex - 1, 'PAV_unit_AD_no_bonus_L_t') + getx(layoutIndex, 'Allocated_unit_L_t')
        }
    }
    /**
     * Policy account value unit before deduce without bonus unit begin of tth month
     * = PAV_unit_AD_no_bonus_t-1 + Allocated_unit_t
     */
    formula "PAV_unit_BD_no_bonus_G_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Allocated_unit_G_t')
        } else {
            getLx(layoutIndex - 1, 'PAV_unit_AD_no_bonus_G_t') + getx(layoutIndex, 'Allocated_unit_G_t')
        }
    }
    /**
     * Total allocated topup unit up to tth month
     */
    formula "Sum_alloc_topup_unit_H_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Alloc_topup_unit_H_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_alloc_topup_unit_H_t') - getx(layoutIndex, 'Alloc_topup_unit_H_t')
        }
    }
    /**
     * Total allocated topup unit up to tth month
     */
    formula "Sum_alloc_topup_unit_L_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Alloc_topup_unit_L_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_alloc_topup_unit_L_t') - getx(layoutIndex, 'Alloc_topup_unit_L_t')
        }
    }
    /**
     * Total allocated topup unit up to tth month
     */
    formula "Sum_alloc_topup_unit_G_t", { i, int index ->
        if (layoutIndex == 0) {
            getx(layoutIndex, 'Alloc_topup_unit_G_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_alloc_topup_unit_G_t') - getx(layoutIndex, 'Alloc_topup_unit_G_t')
        }
    }
    ///////////////// Parallel Area 5 /////////////////////
    /**
     * Policy account value unit before deduce includes bonus unit begin of tth month
     * =PAV_unit_BD_no_bonus_t + Sum_bonus_unit_t-1
     */
    formula "PAV_unit_BD_bonus_H_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_BD_no_bonus_H_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_bonus_unit_H_t') + get('PAV_unit_BD_no_bonus_H_t')
        }
    }
    /**
     * Policy account value unit before deduce includes bonus unit begin of tth month
     * =PAV_unit_BD_no_bonus_t + Sum_bonus_unit_t-1
     */
    formula "PAV_unit_BD_bonus_L_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_BD_no_bonus_L_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_bonus_unit_L_t') + get('PAV_unit_BD_no_bonus_L_t')
        }
    }
    /**
     * Policy account value unit before deduce includes bonus unit begin of tth month
     * =PAV_unit_BD_no_bonus_t + Sum_bonus_unit_t-1
     */
    formula "PAV_unit_BD_bonus_G_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_BD_no_bonus_G_t')
        } else {
            getLx(layoutIndex - 1, 'Sum_bonus_unit_G_t') + get('PAV_unit_BD_no_bonus_G_t')
        }
    }
    /**
     * Average of top-up amount last year by TOTAL Top-up amount allocated into PAV within 02 year preceding the calculation date up to the mth date of calculation year
     */
    formula "Adj_topup_H_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            ((sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_H_t', 'Price_BOM_H_t') + sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_H_t', 'Price_EOM_H_t')) / 2.0 - getLx(layoutIndex - 24, 'Adj_topup_H_t') * (sum(layoutIndex, -11, 'Price_BOM_H_t') + sum(layoutIndex, -11, 'Price_EOM_H_t')) / 2.0) / 12.0
        } else {
            0
        }
    }
    /**
     * Average of top-up amount last year by TOTAL Top-up amount allocated into PAV within 02 year preceding the calculation date up to the mth date of calculation year
     */
    formula "Adj_topup_L_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            ((sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_L_t', 'Price_BOM_L_t') + sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_L_t', 'Price_EOM_L_t')) / 2.0 - getLx(layoutIndex - 24, 'Adj_topup_L_t') * (sum(layoutIndex, -11, 'Price_BOM_L_t') + sum(layoutIndex, -11, 'Price_EOM_L_t')) / 2.0) / 12.0
        } else {
            0
        }
    }
    /**
     * Average of top-up amount last year by TOTAL Top-up amount allocated into PAV within 02 year preceding the calculation date up to the mth date of calculation year
     */
    formula "Adj_topup_G_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            ((sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_G_t', 'Price_BOM_G_t') + sumProduct(layoutIndex, -11, 'Sum_alloc_topup_unit_G_t', 'Price_EOM_G_t')) / 2.0 - getLx(layoutIndex - 24, 'Adj_topup_G_t') * (sum(layoutIndex, -11, 'Price_BOM_G_t') + sum(layoutIndex, -11, 'Price_EOM_G_t')) / 2.0) / 12.0
        } else {
            0
        }
    }
    ///////////////// Parallel Area 6 /////////////////////
    /**
     * Policy account value before deduce:
     * =PAV_unit_BD_bonus_t * Price_BOM
     */
    formula "PAV_BD_H_t", { i, int index ->
        get('PAV_unit_BD_bonus_H_t') * getx(layoutIndex, 'Price_BOM_H_t')
    }
    /**
     * Policy account value before deduce:
     * =PAV_unit_BD_bonus_t * Price_BOM
     */
    formula "PAV_BD_L_t", { i, int index ->
        get('PAV_unit_BD_bonus_L_t') * getx(layoutIndex, 'Price_BOM_L_t')
    }
    /**
     * Policy account value before deduce:
     * =PAV_unit_BD_bonus_t * Price_BOM
     */
    formula "PAV_BD_G_t", { i, int index ->
        get('PAV_unit_BD_bonus_G_t') * getx(layoutIndex, 'Price_BOM_G_t')
    }
    ///////////////// Parallel Area 7 /////////////////////
    /**
     * Policy Account Value before deducting COI at beginning of tth month:
     * PAV_bef_COI_t = PAV_BD_t - Admin_fee
     */
    formula "PAV_bef_COI_H_t", { i, int index ->
        get('PAV_BD_H_t') - 20000.0
    }
    /**
     * Policy Account Value before deducting COI at beginning of tth month:
     * PAV_bef_COI_t = PAV_BD_t - Admin_fee
     */
    formula "PAV_bef_COI_L_t", { i, int index ->
        get('PAV_BD_L_t') - 20000.0
    }
    /**
     * Policy Account Value before deducting COI at beginning of tth month:
     * PAV_bef_COI_t = PAV_BD_t - Admin_fee
     */
    formula "PAV_bef_COI_G_t", { i, int index ->
        get('PAV_BD_G_t') - 20000.0
    }
    ///////////////// Parallel Area 8 /////////////////////
    /**
     * Death Benefit at beginning of tth month:
     * o If DB_opt  = Basic level
     * DB_bom_t = Max (Max( Inc_SI_t, PAV_bef_COI_t) * DB_factor , Total_prem_paid_t , PAV_bef_COI_t)
     * o If DB_opt = Escalating level
     * DB_bom_t = Max ((Inc_SI_t + PAV_bef_COI_t) * DB_factor, Total_prem_paid_t, PAV_bef_COI_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_bom_H_t", { i, int index ->
        def PAV_bef_COI_H_t = get('PAV_bef_COI_H_t')
        def rate = 1.0
        int age = actualAge(true, i.illusDate, i.insuredDOB, layoutIndex)
//        println "actual age: " + age
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            def aa = getx(layoutIndex, 'Inc_SI_t')
            def bb = get('Total_premium_paid_t')
            def a = getMax(aa, PAV_bef_COI_H_t)
            def b = getMax(bb, PAV_bef_COI_H_t)
            getMax(a * rate, b)
//            max(max(getx(layoutIndex, 'Inc_SI_t'), PAV_bef_COI_H_t) * rate, max(get('Total_premium_paid_t'), PAV_bef_COI_H_t))
        } else {
            def bb = get('Total_premium_paid_t')
            def cc = (getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_H_t) * rate
            def b = getMax(bb, PAV_bef_COI_H_t)
            getMax(cc, b)
//            max((getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_H_t) * rate, max(get('Total_premium_paid_t'), PAV_bef_COI_H_t))
        }
    }
    /**
     * Death Benefit at beginning of tth month:
     * o If DB_opt  = Basic level
     * DB_bom_t = Max (Max( Inc_SI_t, PAV_bef_COI_t) * DB_factor , Total_prem_paid_t , PAV_bef_COI_t)
     * o If DB_opt = Escalating level
     * DB_bom_t = Max ((Inc_SI_t + PAV_bef_COI_t) * DB_factor, Total_prem_paid_t, PAV_bef_COI_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_bom_L_t", { i, int index ->
        def PAV_bef_COI_L_t = get('PAV_bef_COI_L_t')
        def rate = 1.0
        int age = actualAge(true, i.illusDate, i.insuredDOB, layoutIndex)
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            def aa = getx(layoutIndex, 'Inc_SI_t')
            def bb = get('Total_premium_paid_t')
            def a = getMax(aa, PAV_bef_COI_L_t)
            def b = getMax(bb, PAV_bef_COI_L_t)
            getMax(a * rate, b)
//            max(max(getx(layoutIndex, 'Inc_SI_t'), PAV_bef_COI_L_t) * rate, max(get('Total_premium_paid_t'), PAV_bef_COI_L_t))
        } else {
            def bb = get('Total_premium_paid_t')
            def cc = (getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_L_t) * rate
            def b = getMax(bb, PAV_bef_COI_L_t)
            getMax(cc, b)
//            max((getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_L_t) * rate, max(get('Total_premium_paid_t'), PAV_bef_COI_L_t))
        }
    }
    /**
     * Death Benefit at beginning of tth month:
     * o If DB_opt  = Basic level
     * DB_bom_t = Max (Max( Inc_SI_t, PAV_bef_COI_t) * DB_factor , Total_prem_paid_t , PAV_bef_COI_t)
     * o If DB_opt = Escalating level
     * DB_bom_t = Max ((Inc_SI_t + PAV_bef_COI_t) * DB_factor, Total_prem_paid_t, PAV_bef_COI_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_bom_G_t", { i, int index ->
        def PAV_bef_COI_G_t = get('PAV_bef_COI_G_t')
        def rate = 1.0
        int age = actualAge(true, i.illusDate, i.insuredDOB, layoutIndex)
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            def aa = getx(layoutIndex, 'Inc_SI_t')
            def bb = get('Total_premium_paid_t')
            def a = getMax(aa, PAV_bef_COI_G_t)
            def b = getMax(bb, PAV_bef_COI_G_t)
            getMax(a * rate, b)
        } else {
            def bb = get('Total_premium_paid_t')
            def cc = (getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_G_t) * rate
            def b = getMax(bb, PAV_bef_COI_G_t)
            getMax(cc, b)
//            def b = getMax(get('Total_premium_paid_t'), PAV_bef_COI_G_t)
//            getMax((getx(layoutIndex, 'Inc_SI_t') + PAV_bef_COI_G_t) * rate, b)
        }
    }
    ///////////////// Parallel Area 9 /////////////////////
    /**
     * Sum Insured at Risk at beginning of tth month:
     * SAAR_t = DB_bom_t – Max(PAV_bef_COI_t – SC_t, 0)
     */
    formula "SAAR_H_t", { i, int index ->
        def aa = get('PAV_bef_COI_H_t') - getx(layoutIndex, 'SC_t')
        def a = getMax(aa, 0.0)
        get('DB_bom_H_t') - a
    }
    /**
     * Sum Insured at Risk at beginning of tth month:
     * SAAR_t = DB_bom_t – Max(PAV_bef_COI_t – SC_t, 0)
     */
    formula "SAAR_L_t", { i, int index ->
        def aa = get('PAV_bef_COI_L_t') - getx(layoutIndex, 'SC_t')
        def a = getMax(aa, 0.0)
        get('DB_bom_L_t') - a
    }
    /**
     * Sum Insured at Risk at beginning of tth month:
     * SAAR_t = DB_bom_t – Max(PAV_bef_COI_t – SC_t, 0)
     */
    formula "SAAR_G_t", { i, int index ->
        def aa = get('PAV_bef_COI_G_t') - getx(layoutIndex, 'SC_t')
        def a = getMax(aa, 0.0)
        get('DB_bom_G_t') - a
    }
    ///////////////// Parallel Area 10 /////////////////////
    /**
     * Cost of Insurance at beginning of tth month:
     * COI_t = SAAR_t * COI_rate * (1 + loading_TI)
     * With COI_rate is Cost of Insurance monthly rate = COI rate /(12 * 1000)
     */
    formula "COI_H_t", { i, int index ->
        def rate
        int year = layoutIndex.intdiv(12)
        int location = (i.age + year) * 2
        if (i.gender == 0) {
            rate = coiRate[location]
        } else {
            rate = coiRate[location + 1]
        }
        get('SAAR_H_t') * (i.loadingRate + 1.0) * rate / (12.0 * 1000.0)
    }
    /**
     * Cost of Insurance at beginning of tth month:
     * COI_t = SAAR_t * COI_rate * (1 + loading_TI)
     * With COI_rate is Cost of Insurance monthly rate = COI rate /(12 * 1000)
     */
    formula "COI_L_t", { i, int index ->
        def rate
        int year = layoutIndex.intdiv(12)
        int location = (i.age + year) * 2
        if (i.gender == 0) {
            rate = coiRate[location]
        } else {
            rate = coiRate[location + 1]
        }
        get('SAAR_L_t') * (i.loadingRate + 1.0) * rate / (12.0 * 1000.0)
    }
    /**
     * Cost of Insurance at beginning of tth month:
     * COI_t = SAAR_t * COI_rate * (1 + loading_TI)
     * With COI_rate is Cost of Insurance monthly rate = COI rate /(12 * 1000)
     */
    formula "COI_G_t", { i, int index ->
        def rate
        int year = layoutIndex.intdiv(12)
        int location = (i.age + year) * 2
        if (i.gender == 0) {
            rate = coiRate[location]
        } else {
            rate = coiRate[location + 1]
        }
        get('SAAR_G_t') * (i.loadingRate + 1.0) * rate / (12.0 * 1000.0)
    }
    ///////////////// Parallel Area 11 /////////////////////
    /**
     * Fee and charge : includes COI and Admin fee unit:
     * =( COI_t + Admin_fee)/Price_BOM
     */
    formula "FC_unit_H_t", { i, int index ->
        (get('COI_H_t') + 20000.0) / getx(layoutIndex, 'Price_BOM_H_t')
    }
    /**
     * Fee and charge : includes COI and Admin fee unit:
     * =( COI_t + Admin_fee)/Price_BOM
     */
    formula "FC_unit_L_t", { i, int index ->
        (get('COI_L_t') + 20000.0) / getx(layoutIndex, 'Price_BOM_L_t')
    }
    /**
     * Fee and charge : includes COI and Admin fee unit:
     * =( COI_t + Admin_fee)/Price_BOM
     */
    formula "FC_unit_G_t", { i, int index ->
        (get('COI_G_t') + 20000.0) / getx(layoutIndex, 'Price_BOM_G_t')
    }
    ///////////////// Parallel Area 12 /////////////////////
    /**
     * Policy Account Value unit after deducted without bonus unit at beginning of tth month:
     * = PAV_unit_BD_no_bonus_t - F&C_unit
     */
    formula "PAV_unit_AD_no_bonus_H_t", { i, int index ->
        get('PAV_unit_BD_no_bonus_H_t') - get('FC_unit_H_t')
    }
    /**
     * Policy Account Value unit after deducted without bonus unit at beginning of tth month:
     * = PAV_unit_BD_no_bonus_t - F&C_unit
     */
    formula "PAV_unit_AD_no_bonus_L_t", { i, int index ->
        get('PAV_unit_BD_no_bonus_L_t') - get('FC_unit_L_t')
    }
    /**
     * Policy Account Value unit after deducted without bonus unit at beginning of tth month:
     * = PAV_unit_BD_no_bonus_t - F&C_unit
     */
    formula "PAV_unit_AD_no_bonus_G_t", { i, int index ->
        get('PAV_unit_BD_no_bonus_G_t') - get('FC_unit_G_t')
    }
    ///////////////// Parallel Area 13 /////////////////////
    /**
     * Policy Account Value before bonus at end of tth month:
     * = (PAV_unit_AD_no_bonus + Sum_bonus_unit_t-1) * Price_EOM
     */
    formula "PAV_BB_H_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_AD_no_bonus_H_t') * getx(layoutIndex, 'Price_EOM_H_t')
        } else {
            ((get('PAV_unit_AD_no_bonus_H_t') + getLx(layoutIndex - 1, 'Sum_bonus_unit_H_t'))) * getx(layoutIndex, 'Price_EOM_H_t')
        }
    }
    /**
     * Policy Account Value before bonus at end of tth month:
     * = (PAV_unit_AD_no_bonus + Sum_bonus_unit_t-1) * Price_EOM
     */
    formula "PAV_BB_L_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_AD_no_bonus_L_t') * getx(layoutIndex, 'Price_EOM_L_t')
        } else {
            ((get('PAV_unit_AD_no_bonus_L_t') + getLx(layoutIndex - 1, 'Sum_bonus_unit_L_t'))) * getx(layoutIndex, 'Price_EOM_L_t')
        }
    }
    /**
     * Policy Account Value before bonus at end of tth month:
     * = (PAV_unit_AD_no_bonus + Sum_bonus_unit_t-1) * Price_EOM
     */
    formula "PAV_BB_G_t", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_unit_AD_no_bonus_G_t') * getx(layoutIndex, 'Price_EOM_G_t')
        } else {
            ((get('PAV_unit_AD_no_bonus_G_t') + getLx(layoutIndex - 1, 'Sum_bonus_unit_G_t'))) * getx(layoutIndex, 'Price_EOM_G_t')
        }
    }
    /**
     * Last 1 year average of Policy Account Value without bonus amount:
     * - If Calender month = 12 then
     *
     * PAV above is not includes any from bonus amount, only calculate on PAV_unit_no_bonus
     * - Else Average_PAV_no_Bonus = 0
     */
    formula "Average_PAV_no_bonus_H_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            (sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_H_t', 'Price_BOM_H_t') + sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_H_t', 'Price_EOM_H_t')) / 2.0 / 12.0
        } else {
            0
        }
    }
    /**
     * Last 1 year average of Policy Account Value without bonus amount:
     * - If Calender month = 12 then
     *
     * PAV above is not includes any from bonus amount, only calculate on PAV_unit_no_bonus
     * - Else Average_PAV_no_Bonus = 0
     */
    formula "Average_PAV_no_bonus_L_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            (sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_L_t', 'Price_BOM_L_t') + sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_L_t', 'Price_EOM_L_t')) / 2.0 / 12.0
        } else {
            0
        }
    }
    /**
     * Last 1 year average of Policy Account Value without bonus amount:
     * - If Calender month = 12 then
     *
     * PAV above is not includes any from bonus amount, only calculate on PAV_unit_no_bonus
     * - Else Average_PAV_no_Bonus = 0
     */
    formula "Average_PAV_no_bonus_G_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            (sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_G_t', 'Price_BOM_G_t') + sumProduct(layoutIndex, -11, 'PAV_unit_AD_no_bonus_G_t', 'Price_EOM_G_t')) / 2.0 / 12.0
        } else {
            0
        }
    }
    /**
     * Loyalty Bonus at end of tth month:
     * LY_Bonus_t is only calculated at the Anniversary Date:
     * LY_bonus_t =(PAV_unit_AD_no_bonus_t -(Sum_alloc_topup_unit_t-Sum_alloc_topup_unit_t-24))* Price_EOM_t *LY_bonus_rate_t
     */
    formula "LY_bonus_H_t", { i, int index ->
        int year = layoutIndex.intdiv(12) + 1
        if (year == 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_H_t') - (get('Sum_alloc_topup_unit_H_t') - getLx(-24, 'Sum_alloc_topup_unit_H_t'))) * getx(layoutIndex, 'Price_EOM_H_t') * 0.015
        } else if (year > 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_H_t') - (get('Sum_alloc_topup_unit_H_t') - getLx(-24, 'Sum_alloc_topup_unit_H_t'))) * getx(layoutIndex, 'Price_EOM_H_t') * 0.01
        } else {
            0
        }
    }
    /**
     * Loyalty Bonus at end of tth month:
     * LY_Bonus_t is only calculated at the Anniversary Date:
     * LY_bonus_t =(PAV_unit_AD_no_bonus_t -(Sum_alloc_topup_unit_t-Sum_alloc_topup_unit_t-24))* Price_EOM_t *LY_bonus_rate_t
     */
    formula "LY_bonus_L_t", { i, int index ->
        int year = layoutIndex.intdiv(12) + 1
        if (year == 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_L_t') - (get('Sum_alloc_topup_unit_L_t') - getLx(-24, 'Sum_alloc_topup_unit_L_t'))) * getx(layoutIndex, 'Price_EOM_L_t') * 0.015
        } else if (year > 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_L_t') - (get('Sum_alloc_topup_unit_L_t') - getLx(-24, 'Sum_alloc_topup_unit_L_t'))) * getx(layoutIndex, 'Price_EOM_L_t') * 0.01
        } else {
            0
        }
    }
    /**
     * Loyalty Bonus at end of tth month:
     * LY_Bonus_t is only calculated at the Anniversary Date:
     * LY_bonus_t =(PAV_unit_AD_no_bonus_t -(Sum_alloc_topup_unit_t-Sum_alloc_topup_unit_t-24))* Price_EOM_t *LY_bonus_rate_t
     */
    formula "LY_bonus_G_t", { i, int index ->
        int year = layoutIndex.intdiv(12) + 1
        if (year == 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_G_t') - (get('Sum_alloc_topup_unit_G_t') - getLx(-24, 'Sum_alloc_topup_unit_G_t'))) * getx(layoutIndex, 'Price_EOM_G_t') * 0.015
        } else if (year > 10 && (layoutIndex + 1) % 12 == 0) {
            (get('PAV_unit_AD_no_bonus_G_t') - (get('Sum_alloc_topup_unit_G_t') - getLx(-24, 'Sum_alloc_topup_unit_G_t'))) * getx(layoutIndex, 'Price_EOM_G_t') * 0.01
        } else {
            0
        }
    }
    ///////////////// Parallel Area 14 /////////////////////
    /**
     * Loyal Bonus unit at the end of tth month:
     * =LY_bonus_t/Price_EOM_t
     */
    formula "LY_bonus_unit_H_t", { i, int index ->
        get('LY_bonus_H_t') / getx(layoutIndex, 'Price_EOM_H_t')
    }
    /**
     * Loyal Bonus unit at the end of tth month:
     * =LY_bonus_t/Price_EOM_t
     */
    formula "LY_bonus_unit_L_t", { i, int index ->
        get('LY_bonus_L_t') / getx(layoutIndex, 'Price_EOM_L_t')
    }
    /**
     * Loyal Bonus unit at the end of tth month:
     * =LY_bonus_t/Price_EOM_t
     */
    formula "LY_bonus_unit_G_t", { i, int index ->
        get('LY_bonus_G_t') / getx(layoutIndex, 'Price_EOM_G_t')
    }
    /**
     * Death Benefit at end of tth month:
     * o If DB_opt  = Basic level
     * DB_eom_t = Max (Max( Inc_SI_t, PAV_BB_t) * DB_factor , Total_prem_paid_t , PAV_BB_t)
     * o If DB_opt = Escalating level
     * DB_eom_t = Max ((Inc_SI_t + PAV_BB_t) * DB_factor, Total_prem_paid_t, PAV_BB_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_eom_H_t", { i, int index ->
        def PAV_BB_H_t = get('PAV_BB_H_t')
        def rate = 1.0
        int age = actualAge(false, i.illusDate, i.insuredDOB, index)
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            getx(layoutIndex, 'Inc_SI_t')
//            getMax(getMax(getx(layoutIndex, 'Inc_SI_t'), PAV_BB_H_t) * rate, getMax(get('Total_premium_paid_t'), PAV_BB_H_t))
        } else {
            def aa = (getx(layoutIndex, 'Inc_SI_t') + PAV_BB_H_t) * rate
            def bb = get('Total_premium_paid_t')
            def a = getMax(bb, PAV_BB_H_t)
            getMax(aa, a)
        }
    }
    /**
     * Death Benefit at end of tth month:
     * o If DB_opt  = Basic level
     * DB_eom_t = Max (Max( Inc_SI_t, PAV_BB_t) * DB_factor , Total_prem_paid_t , PAV_BB_t)
     * o If DB_opt = Escalating level
     * DB_eom_t = Max ((Inc_SI_t + PAV_BB_t) * DB_factor, Total_prem_paid_t, PAV_BB_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_eom_L_t", { i, int index ->
        def PAV_BB_L_t = get('PAV_BB_L_t')
        def rate = 1.0
        int age = actualAge(false, i.illusDate, i.insuredDOB, index)
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            getx(layoutIndex, 'Inc_SI_t')
//            getMax(getMax(getx(layoutIndex, 'Inc_SI_t'), PAV_BB_L_t) * rate, getMax(get('Total_premium_paid_t'), PAV_BB_L_t))
        } else {
            def aa = (getx(layoutIndex, 'Inc_SI_t') + PAV_BB_L_t) * rate
            def bb = get('Total_premium_paid_t')
            def a = getMax(bb, PAV_BB_L_t)
            getMax(aa, a)
        }
    }
    /**
     * Death Benefit at end of tth month:
     * o If DB_opt  = Basic level
     * DB_eom_t = Max (Max( Inc_SI_t, PAV_BB_t) * DB_factor , Total_prem_paid_t , PAV_BB_t)
     * o If DB_opt = Escalating level
     * DB_eom_t = Max ((Inc_SI_t + PAV_BB_t) * DB_factor, Total_prem_paid_t, PAV_BB_t)
     * Note: Escalating level shall be automatically changed to Basic level when the Insured reach 70 years old
     */
    formula "DB_eom_G_t", { i, int index ->
        def PAV_BB_G_t = get('PAV_BB_G_t')
        def rate = 1.0
        int age = actualAge(false, i.illusDate, i.insuredDOB, index)
        if (age < dbFactorRate.length) {
            rate = dbFactorRate[age]
        }
        if (i.DBOpt == 1 || age >= 70) {
            getx(layoutIndex, 'Inc_SI_t')
//            getMax(getMax(getx(layoutIndex, 'Inc_SI_t'), PAV_BB_G_t) * rate, getMax(get('Total_premium_paid_t'), PAV_BB_G_t))
        } else {
            def aa = (getx(layoutIndex, 'Inc_SI_t') + PAV_BB_G_t) * rate
            def bb = get('Total_premium_paid_t')
            def a = getMax(bb, PAV_BB_G_t)
            getMax(aa, a)
        }
    }
    /**
     * Surrender Value at end of tth month:
     * SV_t = max(PAV_BB_t – SC_t,0)
     */
    formula "SV_H_t", { i, int index ->
        def aa = get('PAV_BB_H_t') - getx(layoutIndex, 'SC_t')
        getMax(aa, 0.0)
    }
    /**
     * Surrender Value at end of tth month:
     * SV_t = max(PAV_BB_t – SC_t,0)
     */
    formula "SV_L_t", { i, int index ->
        def aa = get('PAV_BB_L_t') - getx(layoutIndex, 'SC_t')
        getMax(aa, 0.0)
    }
    /**
     * Surrender Value at end of tth month:
     * SV_t = max(PAV_BB_t – SC_t,0)
     */
    formula "SV_G_t", { i, int index ->
        def aa = get('PAV_BB_G_t') - getx(layoutIndex, 'SC_t')
        getMax(aa, 0.0)
    }
    /**
     * Adjustment Policy Account Value at end of tth month:
     * = Average_PAV_no_bonus - Adj_topup
     */
    formula "Adj_PAV_H_t", { i, int index ->
        get('Average_PAV_no_bonus_H_t') - get('Adj_topup_H_t')
    }
    /**
     * Adjustment Policy Account Value at end of tth month:
     * = Average_PAV_no_bonus - Adj_topup
     */
    formula "Adj_PAV_L_t", { i, int index ->
        get('Average_PAV_no_bonus_L_t') - get('Adj_topup_L_t')
    }
    /**
     * Adjustment Policy Account Value at end of tth month:
     * = Average_PAV_no_bonus - Adj_topup
     */
    formula "Adj_PAV_G_t", { i, int index ->
        get('Average_PAV_no_bonus_G_t') - get('Adj_topup_G_t')
    }
    ///////////////// Parallel Area 15 /////////////////////
    /**
     * Loyal Bonus unit accumulation at the end of t th month:
     * =LY_bonus_unit_acc_t-1 + LY_bonus_unit_t
     */
    formula "LY_bonus_unit_acc_H_t", { i, int index ->
        getLx(layoutIndex - 1, 'LY_bonus_unit_acc_H_t') + get('LY_bonus_unit_H_t')
    }
    /**
     * Loyal Bonus unit accumulation at the end of t th month:
     * =LY_bonus_unit_acc_t-1 + LY_bonus_unit_t
     */
    formula "LY_bonus_unit_acc_L_t", { i, int index ->
        getLx(layoutIndex - 1, 'LY_bonus_unit_acc_L_t') + get('LY_bonus_unit_L_t')
    }
    /**
     * Loyal Bonus unit accumulation at the end of t th month:
     * =LY_bonus_unit_acc_t-1 + LY_bonus_unit_t
     */
    formula "LY_bonus_unit_acc_G_t", { i, int index ->
        getLx(layoutIndex - 1, 'LY_bonus_unit_acc_G_t') + get('LY_bonus_unit_G_t')
    }
    /**
     * Fund Size bonus at end of tth month:
     * FS_bonus_t is only calculated at the end of each policy year.
     * With FS_layer_percent as follow:
     * Fund size bonus benefit is the percentage of each accumulated layer of Adjustment Policy Account Value in one year preceding the calculation date as following
     * For example: If Adj_PAV = 1.300.000.000
     * Then this amount is divided into 4 layers
     * 〖PAV_ave_ly_t〗_(layer_1) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_2) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_3) = 500.000.000
     * 〖PAV_ave_ly_t〗_(layer_4) = 1.300.000.000 – (250.000.000 + 250.000.000 + 500.000.000) = 300.000.000
     *  FS_bonus_t = 250.000.000 * 0% + 250.000.000* 0.2% + 500.000.000 * 0.4% + 300.000.000 * 0.6%
     * = 6.300.000
     */
    formula "FS_bonus_H_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            layerCalc(fsBonusRate, get('Adj_PAV_H_t'))
        } else {
            0
        }
    }
    /**
     * Fund Size bonus at end of tth month:
     * FS_bonus_t is only calculated at the end of each policy year.
     * With FS_layer_percent as follow:
     * Fund size bonus benefit is the percentage of each accumulated layer of Adjustment Policy Account Value in one year preceding the calculation date as following
     * For example: If Adj_PAV = 1.300.000.000
     * Then this amount is divided into 4 layers
     * 〖PAV_ave_ly_t〗_(layer_1) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_2) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_3) = 500.000.000
     * 〖PAV_ave_ly_t〗_(layer_4) = 1.300.000.000 – (250.000.000 + 250.000.000 + 500.000.000) = 300.000.000
     *  FS_bonus_t = 250.000.000 * 0% + 250.000.000* 0.2% + 500.000.000 * 0.4% + 300.000.000 * 0.6%
     * = 6.300.000
     */
    formula "FS_bonus_L_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            layerCalc(fsBonusRate, get('Adj_PAV_L_t'))
        } else {
            0
        }
    }
    /**
     * Fund Size bonus at end of tth month:
     * FS_bonus_t is only calculated at the end of each policy year.
     * With FS_layer_percent as follow:
     * Fund size bonus benefit is the percentage of each accumulated layer of Adjustment Policy Account Value in one year preceding the calculation date as following
     * For example: If Adj_PAV = 1.300.000.000
     * Then this amount is divided into 4 layers
     * 〖PAV_ave_ly_t〗_(layer_1) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_2) = 250.000.000
     * 〖PAV_ave_ly_t〗_(layer_3) = 500.000.000
     * 〖PAV_ave_ly_t〗_(layer_4) = 1.300.000.000 – (250.000.000 + 250.000.000 + 500.000.000) = 300.000.000
     *  FS_bonus_t = 250.000.000 * 0% + 250.000.000* 0.2% + 500.000.000 * 0.4% + 300.000.000 * 0.6%
     * = 6.300.000
     */
    formula "FS_bonus_G_t", { i, int index ->
        if ((layoutIndex + 1) % 12 == 0) {
            layerCalc(fsBonusRate, get('Adj_PAV_G_t'))
        } else {
            0
        }
    }
    ///////////////// Parallel Area 16 /////////////////////
    /**
     * Loyal bonus accumulation at end of tth month:
     * LY_bonus_acc_t = (LY_bonus_unit_acc_t-1 + LY_bonus_unit_t) * Price_EOM_t
     */
    formula 'LY_bonus_acc_H_t', { i, int index ->
        get('LY_bonus_unit_acc_H_t') * getx(layoutIndex, 'Price_EOM_H_t')
    }
    /**
     * Loyal bonus accumulation at end of tth month:
     * LY_bonus_acc_t = (LY_bonus_unit_acc_t-1 + LY_bonus_unit_t) * Price_EOM_t
     */
    formula 'LY_bonus_acc_L_t', { i, int index ->
        get('LY_bonus_unit_acc_L_t') * getx(layoutIndex, 'Price_EOM_L_t')
    }
    /**
     * Loyal bonus accumulation at end of tth month:
     * LY_bonus_acc_t = (LY_bonus_unit_acc_t-1 + LY_bonus_unit_t) * Price_EOM_t
     */
    formula 'LY_bonus_acc_G_t', { i, int index ->
        get('LY_bonus_unit_acc_G_t') * getx(layoutIndex, 'Price_EOM_G_t')
    }
    /**
     * Fund Size Bonus unit at the end of tth month:
     * =FS_bonus_t/Price_EOM_t
     */
    formula 'FS_bonus_unit_H_t', { i, int index ->
        get('FS_bonus_H_t') / getx(layoutIndex, 'Price_EOM_H_t')
    }
    /**
     * Fund Size Bonus unit at the end of tth month:
     * =FS_bonus_t/Price_EOM_t
     */
    formula 'FS_bonus_unit_L_t', { i, int index ->
        get('FS_bonus_L_t') / getx(layoutIndex, 'Price_EOM_L_t')
    }
    /**
     * Fund Size Bonus unit at the end of tth month:
     * =FS_bonus_t/Price_EOM_t
     */
    formula 'FS_bonus_unit_G_t', { i, int index ->
        get('FS_bonus_G_t') / getx(layoutIndex, 'Price_EOM_G_t')
    }
    /**
     * Policy Account Value after bonus at end of tth month.
     * PAV_AB_t = PAV_BB_t  + FS_bonus_t + LY_bonus_t
     */
    formula "PAV_AB_H_t", { i, int index ->
        get('PAV_BB_H_t') + get('FS_bonus_H_t') + get('LY_bonus_H_t')
    }
    /**
     * Policy Account Value after bonus at end of tth month.
     * PAV_AB_t = PAV_BB_t  + FS_bonus_t + LY_bonus_t
     */
    formula "PAV_AB_L_t", { i, int index ->
        get('PAV_BB_L_t') + get('FS_bonus_L_t') + get('LY_bonus_L_t')
    }
    /**
     * Policy Account Value after bonus at end of tth month.
     * PAV_AB_t = PAV_BB_t  + FS_bonus_t + LY_bonus_t
     */
    formula "PAV_AB_G_t", { i, int index ->
        get('PAV_BB_G_t') + get('FS_bonus_G_t') + get('LY_bonus_G_t')
    }
    ///////////////// Parallel Area 17 /////////////////////
    /**
     * Fund Size Bonus unit accumulation at the end of t th month:
     * =FS_bonus_unit_acc_t-1 + FS_bonus_unit_t
     */
    formula "FS_bonus_unit_acc_H_t", { i, int index ->
        getLx(layoutIndex - 1, 'FS_bonus_unit_acc_H_t') + get('FS_bonus_unit_H_t')
    }
    /**
     * Fund Size Bonus unit accumulation at the end of t th month:
     * =FS_bonus_unit_acc_t-1 + FS_bonus_unit_t
     */
    formula "FS_bonus_unit_acc_L_t", { i, int index ->
        getLx(layoutIndex - 1, 'FS_bonus_unit_acc_L_t') + get('FS_bonus_unit_L_t')
    }
    /**
     * Fund Size Bonus unit accumulation at the end of t th month:
     * =FS_bonus_unit_acc_t-1 + FS_bonus_unit_t
     */
    formula "FS_bonus_unit_acc_G_t", { i, int index ->
        getLx(layoutIndex - 1, 'FS_bonus_unit_acc_G_t') + get('FS_bonus_unit_G_t')
    }
    /**
     * Sum bonus unit from Loyalty bonus and Fund size bonus:
     * = Sum_bonus_unit_t-1 + LY_bonus_unit_t + FS_bonus_unit_t
     */
    formula "Sum_bonus_unit_H_t", { i, int index ->
        getLx(layoutIndex - 1, 'Sum_bonus_unit_H_t') + get('LY_bonus_unit_H_t') + get('FS_bonus_unit_H_t')
    }
    /**
     * Sum bonus unit from Loyalty bonus and Fund size bonus:
     * = Sum_bonus_unit_t-1 + LY_bonus_unit_t + FS_bonus_unit_t
     */
    formula "Sum_bonus_unit_L_t", { i, int index ->
        getLx(layoutIndex - 1, 'Sum_bonus_unit_L_t') + get('LY_bonus_unit_L_t') + get('FS_bonus_unit_L_t')
    }
    /**
     * Sum bonus unit from Loyalty bonus and Fund size bonus:
     * = Sum_bonus_unit_t-1 + LY_bonus_unit_t + FS_bonus_unit_t
     */
    formula "Sum_bonus_unit_G_t", { i, int index ->
        getLx(layoutIndex - 1, 'Sum_bonus_unit_G_t') + get('LY_bonus_unit_G_t') + get('FS_bonus_unit_G_t')
    }
    ///////////////// Parallel Area 18 /////////////////////
    /**
     * Fund Size bonus accumulation at end of tth month:
     * FS_bonus_acc_t = (FS_bonus_unit_acc_t-1 + FS_bonus_unit_t) * Price_EOM_t
     */
    formula "FS_bonus_acc_H_t", { i, int index ->
        get('FS_bonus_unit_acc_H_t') * getx(layoutIndex, 'Price_EOM_H_t')
    }
    /**
     * Fund Size bonus accumulation at end of tth month:
     * FS_bonus_acc_t = (FS_bonus_unit_acc_t-1 + FS_bonus_unit_t) * Price_EOM_t
     */
    formula "FS_bonus_acc_L_t", { i, int index ->
        get('FS_bonus_unit_acc_L_t') * getx(layoutIndex, 'Price_EOM_L_t')
    }
    /**
     * Fund Size bonus accumulation at end of tth month:
     * FS_bonus_acc_t = (FS_bonus_unit_acc_t-1 + FS_bonus_unit_t) * Price_EOM_t
     */
    formula "FS_bonus_acc_G_t", { i, int index ->
        get('FS_bonus_unit_acc_G_t') * getx(layoutIndex, 'Price_EOM_G_t')
    }


}