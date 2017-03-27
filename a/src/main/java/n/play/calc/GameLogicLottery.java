package n.play.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import n.core.jutils.base.LNUtils;
import n.core.raist.raistlic.common.permutation.Combination;
import n.entity.play.GameMethod;
import n.entity.play.SelectorType;


//删除了选号器，些类是有问题的
public class GameLogicLottery {

    private static final Logger logger = LoggerFactory
            .getLogger(GameLogicLottery.class);

    /**
     * @param ln_num          开奖号码"03 09 4 4 8"
     * @param betNumber       投注号码"0,1,2|0,1,2|0,1,2"
     * @param position        玩法对应的开奖号码位置，比如“后三直选复式”对应开奖号码位置是百十个，即2,3,4 0,1,2
     * @param digit           任选玩法中所选的位置，比如“任选二直选和值”选择了万百个，即0,2,4
     * @param betAmount       单注金额
     * @param method          玩法算法
     * @param selectorType    选号器类型
     * @param selectors       选号器列表
     * @param separator       位间分隔符
     * @param isArbitrarily   是否任选
     * @param prizes          奖级
     * @param betRebatePoint  投注所选返点
     * @param userRebatePoint 用户返点
     * @param rebatePointDiff 返点差
     * @param factors         返点因数
     * @return
     */
    @SuppressWarnings("incomplete-switch")
    public static WinResult calc(String ln_num,
    						     String betNumber,
                                 String position, 
                                 String digit, 
                                 Double betAmount,
                                 GameMethod method,
                                 SelectorType selectorType,
                                 List<?> selectors,
                                 String separator,
                                 boolean isArbitrarily,
                                 List<Double> prizes,
                                 Float betRebatePoint,
                                 Float userRebatePoint,
                                 Float rebatePointDiff,
                                 List<Double> factors) {
    	
        Integer positions[] = LNUtils.toIntegerArray(position == null ? "" : position, ","); // 玩法位置数组
        String selDigit = null;
        if (isArbitrarily) {
            // 任选玩法从投注记录中取投注位
            // 为防止前端恶意投注，进行投注位的校验
            String[] temp = digit.split(",");
            List<String> list = new LinkedList<String>();
            for (int i = 0; i < temp.length; i++) {
                if (!list.contains(temp[i])) {
                    list.add(temp[i]);
                }
            }
            selDigit = StringUtils.join(list.toArray(), ",");
        }
        Integer[] elements = isArbitrarily ? LNUtils.toIntegerArray(selDigit, ",") : positions;
        String lnumsArr[] = ln_num.split(" ", -1); // 开奖号码数组
        String lnums[] = find(lnumsArr, elements); // 玩法对应开奖号码数组，比如后三直选复式则为开奖号码的后三位

        int winCount = 0; // 中奖注数
        double odds = 0; // 赔率

        if (selectorType == SelectorType.DIGITAL) {

            String[][] data = new String[selectors.size()][]; // 号码二维数组，一维：位；二维：号码
            String[] nums = betNumber.split(separator, -1); // 分割位，包含空元素

            for (int i = 0; i < selectors.size(); i++) {
                String[] nos = StringUtils.split(nums[i],
                        null); // 分割号码，不包含空元素，例如【定位胆】
                data[i] = nos;
            }

            switch (method) {
                case ZX5: // 五星直选
                case ZX4: // 四星直选
                case ZX3: // 三星直选
                case ZX2: // 二星直选
                case LTZX3:
                case LTZX2: {
                    winCount = 1;
                    for (int i = 0; i < data.length; i++) {
                        if (!org.apache.commons.lang.ArrayUtils.contains(data[i], lnums[i])) {
                            winCount = 0;
                            break;
                        }
                    }
                    break;
                }
                case ZXZH5: // 五星直选组合
                case ZXZH4: // 四星直选组合
                case ZXZH3: // 三星直选组合
                {
                    for (int i = lnums.length - 1; i >= 0; i--) {
                        if (org.apache.commons.lang.ArrayUtils.contains(data[i], lnums[i])) {
                            winCount++;
                        } else {
                            break;
                        }
                    }
                    for (int i = prizes.size() - winCount; i < prizes.size(); i++) {
                        odds += getOdds(prizes.get(i), userRebatePoint,
                                betRebatePoint, rebatePointDiff, factors.get(i));
                    }
                    break;
                }
                case WXZU120: // 五星组选120
                case SXZU24: // 四星组选24
                case ZU6: // 三星组六
                case ZU2: // 二星组二
                case LTZU3:
                case LTZU2: {
                    int star = 0;
                    switch (method) {
                        case WXZU120:
                            star = 5;
                            break;
                        case SXZU24:
                            star = 4;
                            break;
                        case ZU6:
                        case LTZU3:
                            star = 3;
                            break;
                        case ZU2:
                        case LTZU2:
                            star = 2;
                            break;
                    }
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), star);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        // 开奖号码不重复 且 投注号码包含开奖号码
                        if (!LNUtils.duplicate(lnums)
                                && LNUtils.containsAll(data[0], lnums)) {
                            winCount++;
                        }
                    }
                    break;
                }
                case ZU3: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 3);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        if (xt3(lnums) == XT3.DZ
                                && LNUtils.containsAll(data[0], lnums)) {
                            winCount++;
                        }
                    }
                    break;
                }
                case WXZU60: {
                    Arrays.sort(lnums);
                    String bnums[] = new String[5];
                    // 循环二重号
                    for (String ch : data[0]) {
                        // 循环单号
                        for (int i = 0; i < data[1].length - 2; i++) {
                            for (int j = i + 1; j < data[1].length - 1; j++) {
                                for (int k = j + 1; k < data[1].length; k++) {
                                    String dh1 = data[1][i];
                                    String dh2 = data[1][j];
                                    String dh3 = data[1][k];
                                    if (!dh1.equals(ch) && !dh2.equals(ch)
                                            && !dh3.equals(ch)) {
                                        bnums[0] = ch;
                                        bnums[1] = ch;
                                        bnums[2] = dh1;
                                        bnums[3] = dh2;
                                        bnums[4] = dh3;
                                        Arrays.sort(bnums);
                                        if (Arrays.equals(bnums, lnums)) {
                                            winCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case WXZU30: {
                    Arrays.sort(lnums);
                    String bnums[] = new String[5];
                    // 循环二重号
                    for (int i = 0; i < data[0].length - 1; i++) {
                        for (int j = i + 1; j < data[0].length; j++) {
                            String ch1 = data[0][i];
                            String ch2 = data[0][j];
                            // 循环单号
                            for (String dh : data[1]) {
                                if (!dh.equals(ch1) && !dh.equals(ch2)) {
                                    bnums[0] = ch1;
                                    bnums[1] = ch1;
                                    bnums[2] = ch2;
                                    bnums[3] = ch2;
                                    bnums[4] = dh;
                                    Arrays.sort(bnums);
                                    if (Arrays.equals(bnums, lnums)) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case WXZU20: {
                    Arrays.sort(lnums);
                    String bnums[] = new String[5];
                    // 循环三重号
                    for (String ch : data[0]) {
                        // 循环单号
                        for (int i = 0; i < data[1].length - 1; i++) {
                            for (int j = i + 1; j < data[1].length; j++) {
                                String dh1 = data[1][i];
                                String dh2 = data[1][j];
                                if (!dh1.equals(ch) && !dh2.equals(ch)) {
                                    bnums[0] = ch;
                                    bnums[1] = ch;
                                    bnums[2] = ch;
                                    bnums[3] = dh1;
                                    bnums[4] = dh2;
                                    Arrays.sort(bnums);
                                    if (Arrays.equals(bnums, lnums)) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case WXZU10: {
                    Arrays.sort(lnums);
                    String bnums[] = new String[5];
                    // 循环三重号
                    for (String ch1 : data[0]) {
                        // 循环二重号
                        for (String ch2 : data[1]) {
                            if (!ch1.equals(ch2)) {
                                bnums[0] = ch1;
                                bnums[1] = ch1;
                                bnums[2] = ch1;
                                bnums[3] = ch2;
                                bnums[4] = ch2;
                                Arrays.sort(bnums);
                                if (Arrays.equals(bnums, lnums)) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case WXZU5: {
                    Arrays.sort(lnums);
                    String bnums[] = new String[5];
                    // 循环四重号
                    for (String ch : data[0]) {
                        // 循环单号
                        for (String dh : data[1]) {
                            if (!ch.equals(dh)) {
                                bnums[0] = ch;
                                bnums[1] = ch;
                                bnums[2] = ch;
                                bnums[3] = ch;
                                bnums[4] = dh;
                                Arrays.sort(bnums);
                                if (Arrays.equals(bnums, lnums)) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case SXZU12: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 4);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        Arrays.sort(lnums);
                        String bnums[] = new String[4];
                        // 循环二重号
                        for (String ch : data[0]) {
                            // 循环单号
                            for (int i = 0; i < data[1].length - 1; i++) {
                                for (int j = i + 1; j < data[1].length; j++) {
                                    String dh1 = data[1][i];
                                    String dh2 = data[1][j];
                                    if (!dh1.equals(ch) && !dh2.equals(ch)) {
                                        bnums[0] = ch;
                                        bnums[1] = ch;
                                        bnums[2] = dh1;
                                        bnums[3] = dh2;
                                        Arrays.sort(bnums);
                                        if (Arrays.equals(bnums, lnums)) {
                                            winCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case SXZU6: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 4);
                    
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        Arrays.sort(lnums);
                        String bnums[] = new String[4];
                        // 循环二重号
                        for (int i = 0; i < data[0].length - 1; i++) {
                            for (int j = i + 1; j < data[0].length; j++) {
                                String ch1 = data[0][i];
                                String ch2 = data[0][j];
                                bnums[0] = ch1;
                                bnums[1] = ch1;
                                bnums[2] = ch2;
                                bnums[3] = ch2;
                                Arrays.sort(bnums);
                                if (Arrays.equals(bnums, lnums)) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case SXZU4: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 4);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        Arrays.sort(lnums);
                        String bnums[] = new String[4];
                        // 循环三重号
                        for (String ch : data[0]) {
                            // 循环单号
                            for (String dh : data[1]) {
                                if (!dh.equals(ch)) {
                                    bnums[0] = ch;
                                    bnums[1] = ch;
                                    bnums[2] = ch;
                                    bnums[3] = dh;
                                    Arrays.sort(bnums);
                                    if (Arrays.equals(bnums, lnums)) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case ZXHZ3:
                case ZXHZ2: {
                    int star = 0;
                    switch (method) {
                        case ZXHZ3:
                            star = 3;
                            break;
                        case ZXHZ2:
                            star = 2;
                            break;
                    }
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), star);
                    for (List<Integer> c : p) {
                        int sum = 0;
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        for (String no : lnums) {
                            sum += Integer.parseInt(no);
                        }
                        for (String no : data[0]) {
                            if (Integer.parseInt(no) == sum) {
                                winCount++;
                                break;
                            }
                        }
                    }
                    break;
                }
                case ZXKD3:
                case ZXKD2: {
                    int min, max, diff;
                    min = max = Integer.parseInt(lnums[0]);
                    for (String no : lnums) {
                        int n = Integer.parseInt(no);
                        if (n > max) {
                            max = n;
                        }
                        if (n < min) {
                            min = n;
                        }
                    }
                    diff = max - min;
                    for (String no : data[0]) {
                        if (Integer.parseInt(no) == diff) {
                            winCount = 1;
                            break;
                        }
                    }
                    break;
                }
                case DWD:
                case LTDWD: {
                    for (int i = 0; i < lnums.length; i++) {
                        if (ArrayUtils.contains(data[i], lnums[i])) {
                            winCount++;
                        }
                    }
                    break;
                }
                case BDW1:
                case LTBDW1: {
                    for (String no : data[0]) {
                        if (ArrayUtils.contains(lnums, no)) {
                            winCount++;
                        }
                    }
                    break;
                }
                case BDW2: {
                    for (int i = 0; i < data[0].length - 1; i++) {
                        for (int j = i + 1; j < data[0].length; j++) {
                            if (ArrayUtils.contains(lnums, data[0][i])
                                    && ArrayUtils.contains(lnums, data[0][j])) {
                                winCount++;
                            }
                        }
                    }
                    break;
                }
                case BDW3: {
                    for (int i = 0; i < data[0].length - 2; i++) {
                        for (int j = i + 1; j < data[0].length - 1; j++) {
                            for (int k = j + 1; k < data[0].length; k++) {
                                if (ArrayUtils.contains(lnums, data[0][i])
                                        && ArrayUtils.contains(lnums, data[0][j])
                                        && ArrayUtils.contains(lnums, data[0][k])) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case DXDS: {
                    winCount = 1;
                    for (int i = 0; i < selectors.size(); i++) {
                        String a =  "小";
                        String b =  "单";
                        if (!ArrayUtils.contains(data[i], a)
                                && !ArrayUtils.contains(data[i], b)) {
                            winCount = 0;
                            break;
                        }
                    }
                    break;
                }
                case HZWS3: {
                    int sum = Integer.parseInt(lnums[0])
                            + Integer.parseInt(lnums[1])
                            + Integer.parseInt(lnums[2]);
                    int units = sum % 10;
                    for (String no : data[0]) {
                        if (Integer.parseInt(no) == units) {
                            winCount = 1;
                            break;
                        }
                    }
                    break;
                }
                case TSH3: {
                    if (xt3(lnums) == XT3.BZ && ArrayUtils.contains(data[0], "豹子")) {
                        winCount = 1;
                        odds = getOdds(prizes.get(0), userRebatePoint,
                                betRebatePoint, rebatePointDiff, factors.get(0));
                    }
                    if (xt3(lnums) == XT3.SZ && ArrayUtils.contains(data[0], "顺子")) {
                        winCount = 1;
                        odds = getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff, factors.get(1));
                    }
                    if (xt3(lnums) == XT3.DZ && ArrayUtils.contains(data[0], "对子")) {
                        winCount = 1;
                        odds = getOdds(prizes.get(2), userRebatePoint,
                                betRebatePoint, rebatePointDiff, factors.get(2));
                    }
                    break;
                }
                case YFFS:
                case HSCS:
                case SXBX:
                case SJFC: {
                    int star = 0;
                    switch (method) {
                        case YFFS:
                            star = 1;
                            break;
                        case HSCS:
                            star = 2;
                            break;
                        case SXBX:
                            star = 3;
                            break;
                        case SJFC:
                            star = 4;
                            break;
                        default:
                            break;
                    }
                    for (String no : data[0]) {
                        if (LNUtils.countElements(lnums, no) >= star) {
                            winCount++;
                        }
                    }
                    break;
                }
                case ZUBD2: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 2);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        if (!lnums[0].equals(lnums[1])
                                && ArrayUtils.contains(lnums, data[0][0])) { // 开奖号码不是对子
                            winCount++;
                        }
                    }
                    break;
                }
                case ZUBD3: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 3);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        XT3 xt = xt3(lnums);
                        double rebatePointFactor = xt == XT3.DZ ? 6.665625 : 3.3328125;
                        if (xt == XT3.DZ && ArrayUtils.contains(lnums, data[0][0])) {
                            winCount++;
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    rebatePointFactor);
                        }
                        if (xt == XT3.SZ && ArrayUtils.contains(lnums, data[0][0])) {
                            winCount++;
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    rebatePointFactor);
                        }
                    }
                    break;
                }
                case ZUHZ2: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 2);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        if (!lnums[0].equals(lnums[1])) { // 开奖号码不是对子
                            int sum = Integer.parseInt(lnums[0])
                                    + Integer.parseInt(lnums[1]);
                            for (String no : data[0]) {
                                if (Integer.parseInt(no) == sum) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case ZUHZ3: {
                    Combination<Integer> p = Combination.of(
                            Arrays.asList(elements), 3);
                    for (List<Integer> c : p) {
                        lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                        XT3 xt = xt3(lnums);
                        if (xt != XT3.BZ) {
                            int sum = Integer.parseInt(lnums[0])
                                    + Integer.parseInt(lnums[1])
                                    + Integer.parseInt(lnums[2]);
                            for (String no : data[0]) {
                                if (Integer.parseInt(no) == sum) {
                                    double rebatePointFactor = xt == XT3.DZ ? 6.665625
                                            : 3.3328125;
                                    winCount++;
                                    odds += getOdds(xt == XT3.DZ ? prizes.get(0)
                                                    : prizes.get(1), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            rebatePointFactor);
                                }
                            }
                        }
                    }
                    break;
                }
                case RXZX2: {
                    for (int i = 0; i < 4; i++) {
                        for (int j = i + 1; j < 5; j++) {
                            String a[] = data[i];
                            String b[] = data[j];
                            if (a.length != 0 && b.length != 0) {
                                lnums = find(lnumsArr, new Integer[]{i, j});
                                if (ArrayUtils.contains(a, lnums[0])
                                        && ArrayUtils.contains(b, lnums[1])) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case RXZX3: {
                    for (int i = 0; i < 3; i++) {
                        for (int j = i + 1; j < 4; j++) {
                            for (int k = j + 1; k < 5; k++) {
                                String a[] = data[i];
                                String b[] = data[j];
                                String c[] = data[k];
                                if (a.length != 0 && b.length != 0 && c.length != 0) {
                                    lnums = find(lnumsArr,
                                            new Integer[]{i, j, k});
                                    if (ArrayUtils.contains(a, lnums[0])
                                            && ArrayUtils.contains(b, lnums[1])
                                            && ArrayUtils.contains(c, lnums[2])) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case RXZX4: {
                    for (int i = 0; i < 2; i++) {
                        for (int j = i + 1; j < 3; j++) {
                            for (int k = j + 1; k < 4; k++) {
                                for (int n = k + 1; n < 5; n++) {
                                    String a[] = data[i];
                                    String b[] = data[j];
                                    String c[] = data[k];
                                    String d[] = data[n];
                                    if (a.length != 0 && b.length != 0
                                            && c.length != 0 && d.length != 0) {
                                        lnums = find(lnumsArr, new Integer[]{i,
                                                j, k, n});
                                        if (ArrayUtils.contains(a, lnums[0])
                                                && ArrayUtils.contains(b, lnums[1])
                                                && ArrayUtils.contains(c, lnums[2])
                                                && ArrayUtils.contains(d, lnums[3])) {
                                            winCount++;
                                        }
                                        // System.out.println(Arrays.asList(lnums)+"=="+Arrays.asList(a)+"=="+Arrays.asList(b)+"=="+Arrays.asList(c)+"=="+Arrays.asList(d));
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case LTZUDT3: {
                    String dan[] = data[0];
                    String tuo[] = data[1];
                    Combination<String> p = Combination.of(Arrays.asList(tuo),
                            3 - dan.length);
                    for (List<String> t : p) {
                        String hm[] = (String[]) ArrayUtils
                                .addAll(dan, t.toArray());
                        if (Arrays.equals(LNUtils.sort(hm),
                        		LNUtils.sort(lnums))) {
                            winCount++;
                        }
                    }
                    break;
                }
                case LTZUDT2: {
                    String dan[] = data[0];
                    String tuo[] = data[1];
                    Combination<String> p = Combination.of(Arrays.asList(tuo),
                            2 - dan.length);
                    for (List<String> t : p) {
                        String hm[] = (String[]) ArrayUtils
                                .addAll(dan, t.toArray());
                        if (Arrays.equals(LNUtils.sort(hm),
                        		LNUtils.sort(lnums))) {
                            winCount++;
                        }
                    }
                    break;
                }
                case LTRXDT2:
                case LTRXDT3:
                case LTRXDT4:
                case LTRXDT5:
                case LTRXDT6:
                case LTRXDT7:
                case LTRXDT8: {
                    String dan[] = data[0];
                    String tuo[] = data[1];
                    Combination<String> p = Combination.of(
                            Arrays.asList(tuo),
                            Integer.valueOf(method.name().substring(
                                    method.name().length() - 1))
                                    - dan.length);
                    for (List<String> t : p) {
                        String hm[] = (String[]) ArrayUtils
                                .addAll(dan, t.toArray());
                        switch (method) {
                            case LTRXDT2:
                            case LTRXDT3:
                            case LTRXDT4:
                            case LTRXDT5: {
                                if (LNUtils.containsAll(lnums, hm)) {
                                    winCount++;
                                }
                                break;
                            }
                            case LTRXDT6:
                            case LTRXDT7:
                            case LTRXDT8: {
                                if (LNUtils.containsAll(hm, lnums)) {
                                    winCount++;
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                }
                case LTDDS: {
                    int d = 0;
                    int s = 0;
                    for (String hm : lnums) {
                        if (Integer.parseInt(hm) % 2 == 0) {
                            s++;
                        } else {
                            d++;
                        }
                    }
                    String hm = d + "单" + s + "双";
                    int pi = 0;
                    if (hm.equals("0单5双")) {
                        pi = 0;
                    } else if (hm.equals("5单0双")) {
                        pi = 1;
                    } else if (hm.equals("1单4双")) {
                        pi = 2;
                    } else if (hm.equals("4单1双")) {
                        pi = 3;
                    } else if (hm.equals("2单3双")) {
                        pi = 4;
                    } else if (hm.equals("3单2双")) {
                        pi = 5;
                    }
                    for (String no : data[0]) {
                        if (hm.equals(no)) {
                            winCount++;
                            odds += getOdds(prizes.get(pi), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(pi));
                        }
                    }
                    break;
                }
                case LTCZW: {
                	LNUtils.sort(lnums);
                    String hm = lnums[2];
                    int pi = 0;
                    if (hm.equals("03") || hm.equals("09")) {
                        pi = 0;
                    } else if (hm.equals("04") || hm.equals("08")) {
                        pi = 1;
                    } else if (hm.equals("05") || hm.equals("07")) {
                        pi = 2;
                    } else if (hm.equals("06")) {
                        pi = 3;
                    }
                    for (String no : data[0]) {
                        if (hm.equals(no)) {
                            winCount++;
                            odds += getOdds(prizes.get(pi), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(pi));
                        }
                    }
                    break;
                }
                case LTRX1:
                case LTRX2:
                case LTRX3:
                case LTRX4:
                case LTRX5:
                case LTRX6:
                case LTRX7:
                case LTRX8: {
                    int tc = data[0].length; // 投注号码个数
                    int min = 5; // 最少选择号码个数
                    int cl = tc > 5 ? 5 : tc;
                    int dn = cl > min ? cl - min + 1 : 1;
                    int zc = 0; // 中奖号码个数
                    for (int i = 0; i < tc; i++) {
                        if (ArrayUtils.contains(lnums, data[0][i])) {
                            zc++;
                        }
                    }
                    for (int j = 0; j < dn; j++) {
                        int c = min > 5 ? 5 : min + j;
                        if (zc >= c) {
                            switch (method) {
                                case LTRX1:
                                case LTRX2:
                                case LTRX3:
                                case LTRX4:
                                case LTRX5: {
                                    winCount = LNUtils.combination(c, min);
                                    break;
                                }
                                case LTRX6:
                                case LTRX7:
                                case LTRX8: {
                                    winCount = LNUtils.combination(tc - 5, min - 5);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                }
                case KLBRX1:
                case KLBRX2:
                case KLBRX3:
                case KLBRX4:
                case KLBRX5:
                case KLBRX6:
                case KLBRX7: {
                    int[] min_n = {1, 2, 2, 2, 3, 3, 4};
                    int n = Integer.valueOf(method.name().substring(
                            method.name().length() - 1));
                    int ns = data[0].length;
                    int prizelevels = prizes.size();
                    Map<Integer, int[]> table = new HashMap<Integer, int[]>();
                    for (int i = ns; i >= min_n[n - 1]; i--) {
                        int[] count = new int[prizelevels];
                        for (int j = 1; j <= prizelevels; j++) {
                            count[j - 1] = (ns == 8 && j == 5 && i == 4) ? 0
                                    : LNUtils.getCombinCount(i, n + 1 - j)
                                    * LNUtils.getCombinCount(ns - i, n
                                    - (n + 1 - j));
                        }
                        table.put(i, count);
                    }
                    if (n == 7) {
                        if (ns == 8) {
                            int[] count = new int[prizelevels];
                            for (int j = 1; j <= prizelevels; j++) {
                                count[j - 1] = (j == prizelevels) ? 1 : 0;
                            }
                            table.put(1, count);
                        }
                        int[] count = new int[prizelevels];
                        for (int j = 1; j <= prizelevels; j++) {
                            count[j - 1] = (j == prizelevels) ? (ns == 7 ? 1 : 8)
                                    : 0;
                        }
                        table.put(0, count);
                    }
                    int wc = 0;
                    for (String no : data[0]) {
                        if (ArrayUtils.contains(lnums, no)) {
                            wc++;
                        }
                    }
                    int[] count = table.get(wc);
                    if (count != null) {
                        for (int i = 0; i < count.length; i++) {
                            int c = count[i];
                            winCount += c;
                            odds += c
                                    * getOdds(prizes.get(i), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(i));
                        }
                    }
                    break;
                }
                case KLBSXP: {
                    int a = 0; // 上盘个数
                    int b = 0; // 下盘个数
                    for (String hm : lnums) {
                        if (Integer.parseInt(hm) <= 40) {
                            a++;
                        } else {
                            b++;
                        }
                    }
                    for (String no : data[0]) {
                        if (no.equals("中") && a == b) {
                            winCount++;
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else if (no.equals("上") && a > b) {
                            winCount++;
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        } else if (no.equals("下") && a < b) {
                            winCount++;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                        }
                    }
                    break;
                }
                case KLBJOP: {
                    int a = 0; // 奇盘个数
                    int b = 0; // 偶盘个数
                    for (String hm : lnums) {
                        if (Integer.parseInt(hm) % 2 == 0) {
                            b++;
                        } else {
                            a++;
                        }
                    }
                    for (String no : data[0]) {
                        if (no.equals("和") && a == b) {
                            winCount++;
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else if (no.equals("奇") && a > b) {
                            winCount++;
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        } else if (no.equals("偶") && a < b) {
                            winCount++;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                        }
                    }
                    break;
                }
                case KLBZHZDXDS: {
                    int sum = 0; // 总和值
                    int a = 810;
                    for (String hm : lnums) {
                        sum += Integer.parseInt(hm);
                    }
                    for (String no : data[0]) {
                        if (no.equals("大.单") && sum > a && sum % 2 != 0) {
                            winCount++;
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else if (no.equals("大.双") && sum > a && sum % 2 == 0) {
                            winCount++;
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        } else if (no.equals("小.单") && sum <= a && sum % 2 != 0) {
                            winCount++;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                        } else if (no.equals("小.双") && sum <= a && sum % 2 == 0) {
                            winCount++;
                            odds += getOdds(prizes.get(3), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(3));
                        }
                    }
                    break;
                }
                case PKZX1: {
                    if (ArrayUtils.contains(data[0], lnums[0])) {
                        winCount++;
                    }
                    break;
                }
                case PKZX2: {
                    if (ArrayUtils.contains(data[0], lnums[0])
                            && ArrayUtils.contains(data[1], lnums[1])) {
                        winCount++;
                    }
                    break;
                }
                case PKZX3: {
                    if (ArrayUtils.contains(data[0], lnums[0])
                            && ArrayUtils.contains(data[1], lnums[1])
                            && ArrayUtils.contains(data[2], lnums[2])) {
                        winCount++;
                    }
                    break;
                }
                case PKDWD: {
                    for (int i = 0; i < lnums.length; i++) {
                        if (ArrayUtils.contains(data[i], lnums[i])) {
                            winCount++;
                        }
                    }
                    break;
                }
                case PTKSZH: {//快三总和  
                    int sum = 0;
                    for (String ln : lnums) {
                        sum += Integer.parseInt(ln);
                    }
                    for (String num : betNumber.split(",")) {
                        if (num.equals(sum + "")) {
                            switch (sum) {
                                case 3:
                                case 18:
                                    odds += getOdds(prizes.get(0), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(0));
                                    break;
                                case 4:
                                case 17:
                                    odds += getOdds(prizes.get(1), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(1));
                                    break;
                                case 5:
                                case 16:
                                    odds += getOdds(prizes.get(2), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(2));
                                    break;
                                case 6:
                                case 15:
                                    odds += getOdds(prizes.get(3), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(3));
                                    break;
                                case 7:
                                case 14:
                                    odds += getOdds(prizes.get(4), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(4));
                                    break;
                                case 8:
                                case 13:
                                    odds += getOdds(prizes.get(5), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(5));
                                    break;
                                case 9:
                                case 12:
                                    odds += getOdds(prizes.get(6), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(6));
                                    break;
                                case 10:
                                case 11:
                                    odds += getOdds(prizes.get(7), userRebatePoint,
                                            betRebatePoint, rebatePointDiff,
                                            factors.get(7));
                                    break;
                            }
                            winCount++;
                            break;
                        }
                    }
                    break;
                }
                case PTKS23T: {//快三2,3同号
                    if (lnumsArr[0].equals(lnumsArr[1]) || lnumsArr[1].equals(lnumsArr[2])) {
                        String lnStr = "";
                        for (String ln : lnumsArr) {
                            lnStr += ln;
                        }
                        if (betNumber.equals("全")) {
                            betNumber = "111,222,333,444,555,666";
                        }
                        for (String num : betNumber.split(",")) {
                            if (num.length() == 2) {
                                if (lnStr.contains(num)) {
                                    winCount++;
                                }
                            } else {
                                if (lnStr.equals(num)) {
                                    winCount++;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                case PTKS2BT: {//快三2不同号
                    int count = 0;
                    if (betNumber.indexOf(",") == -1) {//KG玩法
                        nums = new String[]{betNumber.substring(0, 1), betNumber.substring(1, 2)};
                    }
                    for (String num : nums) {
                        if (ln_num.contains(num)) {
                            count++;
                        }
                    }
                    if (count >= 2) {
                        winCount = count * (count - 1) / 2;
                    }
                    break;
                }
                case PTKS3BT: {//快三3不同号
                    int count = 0;
                    for (String num : nums) {
                        if (ln_num.contains(num)) {
                            count++;
                        }
                    }
                    if (count == 3) {
                        winCount++;
                    }
                    break;
                }
                case PTKS3L: {//快三3连号
                    String lnStr = "";
                    for (String ln : lnumsArr) {
                        lnStr += ln;
                    }
                    if (betNumber.equals("全")) {
                        betNumber = "123,234,345,456";
                    }
                    for (String num : betNumber.split(",")) {
                        if (lnStr.equals(num)) {
                            winCount++;
                            break;
                        }
                    }
                    break;
                }
                case ZHDXDS: {//时时彩总和大小单双
                    int sum = 0;
                    int sumNoHalf = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    
                    String[] choseBig =null;
                    if (Integer.parseInt(choseBig[choseBig.length - 1]) < 11) {//总和一半(11选5/快乐8除外)
                        sumNoHalf = ((Integer.parseInt(choseBig[choseBig.length - 1]) - (choseBig.length * 2 - 1)) + (Integer.parseInt(choseBig[choseBig.length - 1]))) * choseBig.length / 2;
                    }
                    if (betNumber.contains("大") || betNumber.equals("总和大")) {
                        if (sum > sumNoHalf)
                            winCount++;
                    }
                    if (betNumber.contains("小") || betNumber.equals("总和小")) {
                        if (sum <= sumNoHalf)
                            winCount++;
                    }
                    if (betNumber.contains("单") || betNumber.equals("总和单")) {
                        if (sum % 2 != 0)
                            winCount++;
                    }
                    if (betNumber.contains("双") || betNumber.equals("总和双")) {
                        if (sum % 2 == 0)
                            winCount++;
                    }
                    break;
                }
                case LHH: {//KG龙虎和(高频彩/PK10)
                    String res = "";
                    int m = 0, n = lnumsArr.length - 1;
                    int digit_ = Integer.parseInt(digit);
                    if (digit_ > 0 && digit_ < 5) {
                        m = digit_;
                        n = positions[positions.length - 1] - digit_;
                    }
                    if (lnumsArr[m].equals(lnumsArr[n])) {
                        res = "和";
                    } else {
                        res = Integer.parseInt(lnumsArr[m]) > Integer.parseInt(lnumsArr[n]) ? "龙" : "虎";
                    }
                    if (betNumber.equals(res)) winCount++;
                    break;
                }
                case PTLHH: {//普通龙虎和
                    String res = "";
                    if (Integer.parseInt(lnums[0]) > Integer.parseInt(lnums[1])) {
                        res = "龙";
                        odds += getOdds(prizes.get(0), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(0));
                    } else if (Integer.parseInt(lnums[0]) < Integer.parseInt(lnums[1])) {
                        res = "虎";
                        odds += getOdds(prizes.get(0), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(0));
                    } else {
                        res = "和";
                        odds += getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(1));
                    }
                    if (betNumber.contains(res)) winCount++;
                    break;
                }
                case KGDXDS: {//时时彩大小单双
                    winCount = 0;
                    String dxds[] = new String[elements.length];
                    for (int i = 0; i < selectors.size(); i++) {
                        String a =   "小";
                        String b =  "单";
                        dxds[i] = a + b;
                        if (elements.length == 1) {//KG玩法
                            if (ArrayUtils.contains(data[i], a) || ArrayUtils.contains(data[i], b)) {
                                winCount++;
                            }
                        }
                    }
                    if (elements.length > 1) {//普通玩法
                        String[] nums0 = nums[0].split(",");
                        String[] nums1 = nums[1].split(",");
                        for (String m : nums0) {
                            for (String n : nums1) {
                                if (dxds[0].contains(m) && dxds[1].contains(n)) {
                                    winCount++;
                                }
                            }
                        }
                    }
                    break;
                }
                case SZP: {//时时彩数字盘
                    winCount = betNumber.equals(Integer.parseInt(lnumsArr[Integer.parseInt(digit)]) + "".trim()) ? 1 : 0;
                    break;
                }
                case GYDXDS: {//pk10冠亚大小单双
                    int sum = Integer.parseInt(lnumsArr[0]) + Integer.parseInt(lnumsArr[1]);
                    switch (betNumber) {
                        case "冠亚大":
                            if (sum > 11) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "冠亚小":
                            if (sum <= 11) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            }
                            break;
                        case "冠亚单":
                            if (sum % 2 != 0) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            }
                            break;
                        case "冠亚双":
                            if (sum % 2 == 0) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                    }
                    break;
                }
                case GYH: {//pk10冠亚和值
                    int sum = Integer.parseInt(lnumsArr[0]) + Integer.parseInt(lnumsArr[1]);
                    winCount = 0;
                    if (betNumber.equals(sum + "".trim())) {
                        winCount = 1;
                        String[] ss1 = {"3", "4", "18", "19"};
                        String[] ss2 = {"5", "6", "16", "17"};
                        String[] ss3 = {"7", "8", "14", "15"};
                        String[] ss4 = {"9", "10", "12", "13"};
                        if (Arrays.asList(ss1).contains(betNumber)) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        }
                        if (Arrays.asList(ss2).contains(betNumber)) {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                        if (Arrays.asList(ss3).contains(betNumber)) {
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                        }
                        if (Arrays.asList(ss4).contains(betNumber)) {
                            odds += getOdds(prizes.get(3), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(3));
                        }
                        if ("11".equals(betNumber)) {
                            odds += getOdds(prizes.get(4), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(4));
                        }
                    }
                    break;
                }
                case PK10DXDS: {//PK10大小单双
                    winCount = 0;
                    for (int i = 0; i < selectors.size(); i++) {
                        
                        String a =   "小";
                        String b =   "单";
                        if (ArrayUtils.contains(data[i], a) || ArrayUtils.contains(data[i], b)) {
                            winCount++;
                        }
                    }
                    break;
                }
                case KSDX: {//快三大小,三粒骰子之点数总和由4点至10点中奖为小;总和由11点至17点开奖为大;注：若三粒骰子平面点数相同，通吃「大」、「小」各注,
                    int sum = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    int num0 = Integer.parseInt(lnumsArr[0]);
                    int num1 = Integer.parseInt(lnumsArr[1]);
                    int num2 = Integer.parseInt(lnumsArr[2]);
                    if ((betNumber.contains("大") && sum > 10) && (num0 != num1 && num1 != num2)) {
                        winCount++;
                    }
                    if ((betNumber.contains("小") && sum < 11) && (num0 != num1 && num1 != num2)) {
                        winCount++;
                    }
                    break;
                }

                case PCDDHZ: {//pc蛋蛋和值
                    winCount = 0;
                    int sum = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    if (betNumber.equals(sum + "".trim())) {
                        winCount = 1;
                        String[] ss1 = {"0", "27"};
                        String[] ss2 = {"13", "14"};
                        if (Arrays.asList(ss1).contains(betNumber)) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else if (Arrays.asList(ss2).contains(betNumber)) {
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                        } else {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                    }
                    break;
                }
                case PCDDDXDS: { // pc蛋蛋大小单双
                    int sum = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    switch (betNumber) {
                        case "大":
                            if (sum > 14) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            } else if (sum == 14) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            }
                            break;
                        case "小":
                            if (sum < 13) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            } else if (sum == 13) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            }
                            break;
                        case "单":
                            if (sum == 13) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            } else if (sum % 2 != 0) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "双":
                            if (sum == 14) {
                                winCount++;
                                odds += getOdds(prizes.get(1), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(1));
                            } else if (sum % 2 == 0) {
                                winCount++;
                                odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                    }
                    break;
                }
                case PCDDJZ: { //pc蛋蛋极值([极小0-5]  [极大22-27] )
                    int sum = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    String[] ss1 = {"0", "1", "2", "3", "4", "5"};
                    String[] ss2 = {"22", "23", "24", "25", "26", "27"};
                    if (betNumber.equals("极小") && Arrays.asList(ss1).contains(sum+"")) {
                        winCount = 1;
                    }
                    if (betNumber.equals("极大") && Arrays.asList(ss2).contains(sum+"")) {
                        winCount = 1;
                    }
                    break;
                }
                case PCDDZH: {//pc蛋蛋组合(大单,大双,小单,小双)
                    int sum = 0;
                    int a = 13;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    if (betNumber.equals("大单") && sum > a && sum % 2 != 0) {
                        winCount++;
                        if (sum > (a + 1)) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                    } else if (betNumber.equals("大双") && sum > a && sum % 2 == 0) {
                        winCount++;
                        if (sum > (a + 1)) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                    } else if (betNumber.equals("小单") && sum <= a && sum % 2 != 0) {
                        winCount++;
                        if (sum < a) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                    } else if (betNumber.equals("小双") && sum <= a && sum % 2 == 0) {
                        winCount++;
                        if (sum < a) {
                            odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
                        } else {
                            odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
                        }
                    }
                    break;
                }
                case PCDDBS: { //pc蛋蛋波色(红波,蓝波,绿波)
                    int sum = 0;
                    String colour[] = new String[8];
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    switch (betNumber) {
                        case "红波":
                            colour = new String[]{"3", "9", "15", "21", "6", "12", "18", "24"};
                            break;
                        case "绿波":
                            colour = new String[]{"1", "4", "7", "10", "16", "19", "22", "25"};
                            break;
                        case "蓝波":
                            colour = new String[]{"2", "5", "8", "11", "17", "20", "23", "26"};
                            break;
                    }
                    if (Arrays.asList(colour).contains(sum+"")) winCount++;
                    break;
                }
                case PCDDBZ: { //pc蛋蛋豹子
                    if (lnumsArr[0] != "" && lnumsArr[0].equals(lnumsArr[1]) && lnumsArr[1].equals(lnumsArr[2]))
                        winCount++;
                    break;
                }
                case PCDDTMBS: {//pc蛋蛋特码包三
                    int sum = 0;
                    for (String ln : lnumsArr) {
                        sum += Integer.parseInt(ln);
                    }
                    String[] bn = betNumber.split("\\|");
                    for (String num : bn) {
                        if (num.equals(sum + "")) {
                            winCount++;
                            break;
                        }
                    }
                    break;
                }
                case XGCDXDS: {//香港六合彩大小单双：
//                	特小开出的特码，(01~24)小于或等于24。
//                	特大：开出的特码，(25~48)小于或等于48。//                	
//                	特双：特码为双数，如18、20、34、42。
//                	特单：特码为单数，如01，11，35，47。
//                	和局：特码为49时。
                	int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                	if(tenum != 49){
                		winCount = 1;
	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(1));
	                    break;
                	}
                    switch (betNumber) {
                        case "特大":
                            if (tenum >= 25) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特小":
                            if (tenum <= 24) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特单":
                            if (tenum % 2 != 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特双":
                            if (tenum % 2 == 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                    }
                	break;
                }
                case XGCHDXDS: {//香港六合彩合大小单双
//                	合数大：特码的个位加上十位之和来决定大小，和数大于或等于7为大。
//                	合数小：特码的个位加上十位之和来决定大小，和数小于或等于6为小。
//                	特双：指开出特码的个位加上十位之和为‘双数’，如02，11，33，44。
//                	特单：指开出特码的个位加上十位之和为‘单数’，如01，14，36，47。
//                	和局：特码为49时。
                	int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                	if(tenum != 49){
                		winCount = 1;
	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(1));
	                    break;
                	}
            		int tesum = 0;
            		while(tenum != 0)  {  
            			tesum += tenum % 10 ;   
                        tenum /= 10 ;  
                    }  
                    switch (betNumber) {
                        case "特合大":
                            if (tesum >= 7) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特合小":
                            if (tesum <= 6) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特合单":
                            if (tesum % 2 != 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特合双":
                            if (tesum % 2 == 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                    }
                	break;
                }
                case XGCTWDX: {// 香港六合彩特尾大小
//                	特尾大：5尾~9尾为大，如05、18、19。
//                	特尾小：0尾~4尾为小，如01，32，44。
//                	和局：特码为49时。
                	int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                	if(tenum != 49){
                		winCount = 1;
	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(1));
	                    break;
                	}
                    switch (betNumber) {
                        case "特尾大":
                            if (tenum%10 >= 5) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特尾小":
                            if (tenum%10 <= 4) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                        break;
                    }
                	break;
                }
                case  XGCTMBT: {//香港六合彩特码半特
//                	以特码大小与特码单双游戏为一个投注组合；当期特码开出符合投注组合，即视为中奖；若当期特码开出49号，其余情形视为不中奖。
//                	大单：25、27、29、31、33、35、37、39，41、43、45、47
//                	大双：26、28、30、32、34、36、38、40，42、44、46、48
//                	小单：01、03、05、07、09、11、13、15，17、19、21、23
//                	小双：02、04、06、08、10、12、14、16，18、20、22、24
//                	和局：特码为49时。
                	int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                	if(tenum != 49){
                		winCount = 1;
	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                betRebatePoint, rebatePointDiff,
                                factors.get(1));
	                    break;
                	}
                    switch (betNumber) {
                        case "特大单":                        	
                            if (tenum >= 25 && tenum % 2 != 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特大双":
                            if (tenum >= 25 && tenum % 2 == 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特小单":
                            if (tenum <= 24 &&tenum % 2 != 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                        case "特小双":
                            if (tenum <= 24 &&tenum % 2 == 0) {
                            	winCount = 1;
        	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                        betRebatePoint, rebatePointDiff,
                                        factors.get(0));
                            }
                            break;
                    }
                	break;
                }                
                case XGCSB: { //香港六合彩色波
                	String[] colour1 = {"01","02","07","08","12","13","18","19","23","24","29","30","34","35","40","45","46"};
                	String[] colour2 = {"03","04","09","10","14","15","20","25","26","31","36","37","41","42","47","48"};
                	String[] colour3 = {"05","06","11","16","17","21","22","27","28","32","33","38","39","43","44","49"};
                	int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                	//红波
                	if(Arrays.asList(colour1).contains(tenum+"")){
                		if (betNumber.equals("红波")) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
    	                    break;
                		}else if (betNumber.equals("红单") && tenum % 2 != 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("红双") && tenum % 2 == 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("红大") && tenum >= 25) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("红小") && tenum <= 24) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("红大单") && tenum >= 25 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("红大双") && tenum >= 25 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("红小单") && tenum <= 24 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("红小双") && tenum <= 24 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }                		
                	}
                	//蓝波
                	if(Arrays.asList(colour2).contains(tenum+"")){
                		if (betNumber.equals("蓝波")) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
    	                    break;
                		}else if (betNumber.equals("蓝单") && tenum % 2 != 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("蓝双") && tenum % 2 == 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("蓝大") && tenum >= 25) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("蓝小") && tenum <= 24) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("蓝大单") && tenum >= 25 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("蓝大双") && tenum >= 25 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("蓝小单") && tenum <= 24 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("蓝小双") && tenum <= 24 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }                		
                	}
                	//绿波
                	if(Arrays.asList(colour3).contains(tenum+"")){
                		if (betNumber.equals("绿波")) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(0), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(0));
    	                    break;
                		}else if (betNumber.equals("绿单") && tenum % 2 != 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("绿双") && tenum % 2 == 0) {
    	                    winCount = 1;
    	                    odds += getOdds(prizes.get(1), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(1));
    	                    break;
                		}else if (betNumber.equals("绿大") && tenum >= 25) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("绿小") && tenum <= 24) {
                             winCount= 1;
                             odds += getOdds(prizes.get(1), userRebatePoint,
                                     betRebatePoint, rebatePointDiff,
                                     factors.get(1));
                             break;
                        }else if (betNumber.equals("绿大单") && tenum >= 25 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("绿大双") && tenum >= 25 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("绿小单") && tenum <= 24 && tenum % 2 != 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }else if (betNumber.equals("绿小双") && tenum <= 24 && tenum % 2 == 0) {
                            winCount= 1;
                            odds += getOdds(prizes.get(2), userRebatePoint,
                                    betRebatePoint, rebatePointDiff,
                                    factors.get(2));
                            break;
                        }                		
                	}	                
                	break;
                	
//                	总和大小单双
//                	总和大：所以七个开奖号码的分数总和大于或等于175。
//                	总和小：所以七个开奖号码的分数总和小于或等于174。
//                	总和单：所以七个开奖号码的分数总和是‘单数’，如分数总和是133、197。
//                	总和双：所以七个开奖号码的分数总和是‘双数’，如分数总和是120、188。
                	
                	
//                	特码生肖
//                	生肖顺序为 鼠 >牛 >虎 >兔 >龙 >蛇 >马 >羊 >猴 >鸡 >狗 >猪 。
//                	如今年是猴年，就以猴为开始，依顺序将49个号码分为12个生肖『如下』
//                	猴：01、13、25、37、49
//                	羊：12、24、36、48
//                	马：11、23、35、47
//                	蛇：10、22、34、46
//                	龙：09、21、33、45
//                	兔：08、20、32、44
//                	虎：07、19、31、43
//                	牛：06、18、30、42
//                	鼠：05、17、29、41
//                	猪：04、16、28、40
//                	狗：03、15、27、39
//                	鸡：02、14、26、38
//                	若当期特别号，落在下注生肖范围内，视为中奖 。
                	
//                	正肖
//                	以开出的6个正码做游戏，只要有1个落在下注生肖范围内，视为中奖。如超过1个正码落在下注生肖范围内 ，派彩将倍增！如：下注＄100.-正肖猴倍率1.8。
//                	6个正码开出01，派彩为＄80
//                	6个正码开出01，13，派彩为＄160
//                	6个正码开出01，13，25，派彩为＄240
//                	6个正码开出01，13，25，37，派彩为＄320
//                	6个正码开出01，13，25，37，49，派彩为＄320
                	
//                	特码头数
//                	特码头数：是指特码属头数的号码
//                	"0"头：01、02、03、04、05、06、07、08、09
//                	"1"头：10、11、12、13、14、15、16、17、18、19
//                	"2"头：20、21、22、23、24、25、26、27、28、29
//                	"3"头：30、31、32、33、34、35、36、37、38、39
//                	"4"头：40、41、42、43、44、45、46、47、48、49
//                	例如：开奖结果特别号码为21则2头为中奖，其他头数都不中奖。
//                	
//                	特码尾数
//                	特码尾数：是指特码属尾数的号码。
//                	"0"尾：10、20、30、40
//                	"1"尾：01、11、21、31、41
//                	"2"尾：02、12、22、32、42
//                	"3"尾：03、13、23、33、43
//                	"4"尾：04、14、24、34、44
//                	"5"尾：05、15、25、35、45
//                	"6"尾：06、16、26、36、46
//                	"7"尾：07、17、27、37、47
//                	"8"尾：08、18、28、38、48
//                	"9"尾：09、19、29、39、49
//                	例如：开奖结果特别号码为21则1尾数为中奖，其他尾数都不中奖。
                	
//                	五行
//                	挑选一个五行选项为一个组合，若开奖号码的特码在此组合内，即视为中奖；若开奖号码的特码亦不在此组合内，即视为不中奖；
//                	金：01、02、15、16、23、24、31、32、45、46
//                	木：05、06、13、14、27、28、35、36、43、44
//                	水：03、04、11、12、19、20、33、34、41、42、49
//                	火：07、08、21、22、29、30、37、38
//                	土：09、10、17、18、25、26、39、40、47、48
                }
                default:
                    break;
            }
        }

        if (selectorType == SelectorType.TEXT) {
            try {
                String[] data = betNumber.split(separator, -1);

                switch (method) {
                    case ZX5:
                    case ZX4:
                    case ZX3:
                    case ZX2:
                    case LTZX3:
                    case LTZX2:
                    case PKZX2:
                    case PKZX3: {
                        int star = 0;
                        switch (method) {
                            case ZX5:
                                star = 5;
                                break;
                            case ZX4:
                                star = 4;
                                break;
                            case ZX3:
                            case LTZX3:
                            case PKZX3:
                                star = 3;
                                break;
                            case ZX2:
                            case LTZX2:
                            case PKZX2:
                                star = 2;
                                break;
                        }
                        Combination<Integer> p = Combination.of(
                                Arrays.asList(elements), star);
                        for (List<Integer> c : p) {
                            lnums = find(lnumsArr, c.toArray(new Integer[c.size()]));
                            for (String no : data) {
                                if (LNUtils.join(lnums, "").equals(no)) {
                                    winCount++;
                                }
                            }
                        }
                        break;
                    }
                    case ZU3: {
                        for (String tmpLnums : ZUHH3Number(lnums)) {
                            String[] tmpLnum = tmpLnums.split(",");
                            if (xt3(tmpLnum) == XT3.DZ) {
                                for (String no : data) {
                                    String nos[] = LNUtils.partition(no, 1).toArray(
                                            new String[3]);
                                    if (Arrays.equals(LNUtils.sort(nos),
                                    		LNUtils.sort(tmpLnum))) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case ZU6: {
                        for (String tmpLnums : ZUHH3Number(lnums)) {
                            String[] tmpLnum = tmpLnums.split(",");
                            if (xt3(tmpLnum) == XT3.SZ) {
                                for (String no : data) {
                                    String nos[] = LNUtils.partition(no, 1).toArray(
                                            new String[3]);
                                    if (Arrays.equals(LNUtils.sort(nos),
                                    		LNUtils.sort(tmpLnum))) {
                                        winCount++;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case LTZU3: {
                        int p = 2;
                        if (xt3(lnums) == XT3.SZ) {
                            for (String no : data) {
                                String nos[] = LNUtils.partition(no, p).toArray(
                                        new String[3]);
                                if (Arrays.equals(LNUtils.sort(nos),
                                		LNUtils.sort(lnums))) {
                                    winCount++;
                                }
                            }
                        }
                        break;
                    }
                    case ZUHH3: {
                        // 三星组选混合任选玩法至少选择三位,最多可选择5位
                        for (String tmpLnums : ZUHH3Number(lnums)) {
                            String[] tmpLnum = tmpLnums.split(",");
                            XT3 xt = xt3(tmpLnum);
                            if (xt != XT3.BZ) {
                                for (String no : data) {
                                    String nos[] = LNUtils.partition(no, 1).toArray(
                                            new String[3]);
                                    if (xt3(nos) != XT3.BZ) {
                                        if (Arrays.equals(LNUtils.sort(nos),
                                        		LNUtils.sort(tmpLnum))) {
                                            double rebatePointFactor = xt == XT3.DZ ? 6.665625
                                                    : 3.3328125;
                                            winCount++;
                                            odds += getOdds(xt == XT3.DZ ? prizes.get(0)
                                                            : prizes.get(1), userRebatePoint,
                                                    betRebatePoint, rebatePointDiff,
                                                    rebatePointFactor);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case ZU2:
                    case LTZU2: {
                        int p = 1;
                        switch (method) {
                            case ZU2: {
                                p = 1;
                                break;
                            }
                            case LTZU2: {
                                p = 2;
                                break;
                            }
                            default:
                                break;
                        }
                        if (!lnums[0].equals(lnums[1])) { // 不是对子号
                            for (String no : data) {
                                String nos[] = LNUtils.partition(no, p).toArray(
                                        new String[2]);
                                if (Arrays.equals(LNUtils.sort(nos),
                                		LNUtils.sort(lnums))) {
                                    winCount++;
                                }
                            }
                        }
                        break;
                    }
                    case LTRX1:
                    case LTRX2:
                    case LTRX3:
                    case LTRX4:
                    case LTRX5:
                    case LTRX6:
                    case LTRX7:
                    case LTRX8: {
                        int star = Integer.valueOf(method.name().substring(
                                method.name().length() - 1));
                        int min = star > 5 ? 5 : star; // 最少选择号码个数
                        for (String no : data) {
                            String nos[] = LNUtils.partition(no, 2).toArray(
                                    new String[star]);
                            int n = 0;
                            for (String hm : lnums) {
                                if (ArrayUtils.contains(nos, hm)) {
                                    n++;
                                }
                            }
                            if (n >= min) {
                                winCount++;
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }

            } catch (Exception ex) {
                winCount = 0;
                logger.error("", ex);
            }
        }
        WinResult gameResult = new WinResult(); // 计算结果
        if (winCount > 0) {
            gameResult.setIsWin(Boolean.TRUE);
            gameResult.setWinCount(winCount);
            if (prizes.size() == 1) {
                gameResult.setWinAmount(getOdds(prizes.get(0), userRebatePoint,
                        betRebatePoint, rebatePointDiff, factors.get(0))
                        * betAmount * winCount);
            } else {
                gameResult.setWinAmount(odds * betAmount);
            }
        }
        return gameResult;
    }

    /**
     * 奖金 ＝ 玩法奖金 + (用户返点-玩法返点差-投注所选返点) * 返点因数 返点因数 ＝ (0返点的奖金 - 返点奖金) / 返点
     *
     * @param bonus
     * @param ur
     * @param br
     * @param diff
     * @param factor
     * @return
     */
    private static double getBonus(double bonus, float ur, float br,
                                   float diff, double factor) {
        float rebate = ur - diff - br;
        return bonus + (rebate < 0 ? 0 : rebate) * factor;
    }

    /**
     * 赔率 ＝ 奖金/2
     *
     * @param bonus
     * @param ur
     * @param br
     * @param diff
     * @param factor
     * @return
     */
    private static double getOdds(double bonus, float ur, float br, float diff,
                                  double factor) {
        return getBonus(bonus, ur, br, diff, factor) / 2;
    }

    /**
     * 查找指定位置的号码
     *
     * @param nums
     * @param positions
     * @return
     */
    private static String[] find(String[] nums, Integer[] positions) {
        String result[] = new String[positions.length];
        for (int i = 0; i < positions.length; i++) {
            result[i] = nums[positions[i]];
        }
        return result;
    }

    /**
     * 三星形态
     *
     * @param nums
     * @return
     */
    private static XT3 xt3(String[] nums) {
        String first = nums[0];
        String second = nums[1];
        String third = nums[2];
        if (first.equals(second) && second.equals(third)) {
            return XT3.BZ;
        }
        if (first.equals(second) || first.equals(third) || second.equals(third)) {
            return XT3.DZ;
        }
        return XT3.SZ;
    }

    /**
     * 三星组选混合任选玩法至少选择三位,最多可选择5位
     *
     * @param arr
     * @return
     */
    private static String[] ZUHH3Number(String[] arr) {
        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    strList.add(arr[i] + "," + arr[j] + "," + arr[k]);
                }
            }
        }
        String[] lnums = strList.toArray(new String[strList.size()]);
        return lnums;
    }

    public static enum XT3 {
        /**
         * 豹子 111
         */
        BZ,
        /**
         * 顺子 123
         */
        SZ,
        /**
         * 对子 112
         */
        DZ
    }
}
