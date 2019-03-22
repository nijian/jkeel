import com.github.nijian.jkeel.algorithms.serration.CalcConfig

CalcConfig {

    cid '[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN_LB, illustrationVersion:v1]'

    /**
     * in order to reach better performance, rate table is defined in array format
     */

    /**
     * Guarantee interest rate table
     */
    final
    BigDecimal[] giRate = [0.0041, 0.0037, 0.0033, 0.0033, 0.0029, 0.0025, 0.0025, 0.0025, 0.0025, 0.0025]

    /**
     * convert raw input (quotation instance) to simple format for simplifying formula
     */
    convert "Convert", { rawInput, var, i ->
        i.illusDate = new Date(118, 7, 24)
        i.polTermOpt = 1

        i.lbStartIndex = var.lbStartIndex
        i.lbTermM = var.lbTermM
        i.unitPriceMapH = var.unitPriceMapH
        i.unitPriceMapL = var.unitPriceMapL
        i.unitPriceMapG = var.unitPriceMapG
        i.PAV_AB_end_H = var.PAV_AB_end_H
        i.PAV_AB_end_L = var.PAV_AB_end_L
        i.PAV_AB_end_G = var.PAV_AB_end_G
    }

    strategy "OUTPUT", { i ->
        putLGroupFirst 'LB_payment_H', 12, 'LB_payment_H_k'
        putLGroupFirst 'LB_payment_L', 12, 'LB_payment_L_k'
        putLGroupFirst 'LB_payment_G', 12, 'LB_payment_G_k'
    }
    /**
     * calculate benefit month by month
     */
    strategy "LayoutCount", { i ->
        i.lbTermM
    }

    ///////////////////////////////////// Calculation Layout 1 //////////////////
    ///////////////// Parallel Area 1 /////////////////////
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_BOM_H_k", { i, int index ->
        i.unitPriceMapH[dateStr(i.illusDate, i.lbStartIndex + layoutIndex)]
    }
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_BOM_L_k", { i, int index ->
        i.unitPriceMapL[dateStr(i.illusDate, i.lbStartIndex + layoutIndex)]
    }
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_BOM_G_k", { i, int index ->
        i.unitPriceMapG[dateStr(i.illusDate, i.lbStartIndex + layoutIndex)]
    }
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_EOM_H_k", { i, int index ->
        i.unitPriceMapH[dateStr(i.illusDate, i.lbStartIndex + layoutIndex, true)]
    }
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_EOM_L_k", { i, int index ->
        i.unitPriceMapL[dateStr(i.illusDate, i.lbStartIndex + layoutIndex, true)]
    }
    /**
     * Price of unit of first day of kth month
     */
    formula "Price_EOM_G_k", { i, int index ->
        i.unitPriceMapG[dateStr(i.illusDate, i.lbStartIndex + layoutIndex, true)]
    }
    formula "LB_rem_month_k", { i, int index ->
        i.lbTermM - layoutIndex
    }
    ///////////////// Parallel Area 2 /////////////////////
    /**
     * PAV unit before deduct of Longevity period at begin of kth month:
     * At k = 0 , PAV_Longevity_BD_unit_k = PAV_AB_end/Price_EOM_k
     * Otherwise, PAV_Longevity_BD_unit_k = PAV_Longevity_AD_unit_k-1
     * With:
     * If Maturity_opt = 90 then PAV_AB_end is the PAV_AB_t of the last month when Age_t =89
     * If Maturity_opt = 95/100 then PAV_AB_end is the PAV_AB_t of the last month when Age_t =94/99
     */
    formula "PAV_Longevity_BD_unit_H_k", { i, int index ->
        if (layoutIndex == 0) {
            i.PAV_AB_end_H / get('Price_EOM_H_k')
        } else {
            getLx(layoutIndex - 1, 'PAV_Longevity_AD_unit_H_k')
        }
    }
    formula "PAV_Longevity_BD_unit_L_k", { i, int index ->
        if (layoutIndex == 0) {
            i.PAV_AB_end_L / get('Price_EOM_L_k')
        } else {
            getLx(layoutIndex - 1, 'PAV_Longevity_AD_unit_L_k')
        }
    }
    formula "PAV_Longevity_BD_unit_G_k", { i, int index ->
        if (layoutIndex == 0) {
            i.PAV_AB_end_G / get('Price_EOM_G_k')
        } else {
            getLx(layoutIndex - 1, 'PAV_Longevity_AD_unit_G_k')
        }
    }
    ///////////////// Parallel Area 3 /////////////////////
    formula "LB_payment_H_k", { i, int index ->
        int LB_rem_month_k = get('LB_rem_month_k')
        if (LB_rem_month_k % 12 == 0) {
            get('PAV_Longevity_BD_unit_H_k') * get('Price_BOM_H_k') / (LB_rem_month_k / 12)
        } else {
            0
        }
    }
    formula "LB_payment_L_k", { i, int index ->
        int LB_rem_month_k = get('LB_rem_month_k')
        if (LB_rem_month_k % 12 == 0) {
            get('PAV_Longevity_BD_unit_L_k') * get('Price_BOM_L_k') / (LB_rem_month_k / 12)
        } else {
            0
        }
    }
    formula "LB_payment_G_k", { i, int index ->
        int LB_rem_month_k = get('LB_rem_month_k')
        if (LB_rem_month_k % 12 == 0) {
            get('PAV_Longevity_BD_unit_G_k') * get('Price_BOM_G_k') / (LB_rem_month_k / 12)
        } else {
            0
        }
    }
    ///////////////// Parallel Area 4 /////////////////////
    formula "PAV_Longevity_AD_unit_H_k", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_Longevity_BD_unit_H_k')
        } else {
            get('PAV_Longevity_BD_unit_H_k') - (20000 + get('LB_payment_H_k')) / get('Price_BOM_H_k')
        }
    }
    formula "PAV_Longevity_AD_unit_L_k", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_Longevity_BD_unit_L_k')
        } else {
            get('PAV_Longevity_BD_unit_L_k') - (20000 + get('LB_payment_L_k')) / get('Price_BOM_L_k')
        }
    }
    formula "PAV_Longevity_AD_unit_G_k", { i, int index ->
        if (layoutIndex == 0) {
            get('PAV_Longevity_BD_unit_G_k')
        } else {
            get('PAV_Longevity_BD_unit_G_k') - (20000 + get('LB_payment_G_k')) / get('Price_BOM_G_k')
        }
    }


}