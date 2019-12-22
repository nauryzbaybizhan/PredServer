package avtostavka;

public class BookmakerDataParser {

    public int countChar(String str, char c) {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count;
    }

    static int spaceCount(String s) {
        int i,c,res;
        for(i=0,c=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(ch==' ')
                c++;
        }
        return c;
    }


    public boolean containsChar(String s, char search) {
        if (s.length() == 0)
            return false;
        else
            return s.charAt(0) == search || containsChar(s.substring(1), search);
    }

    public int[] parseScore(String score) {
        int[] retData = new int[3];
        if (score.isEmpty()) {
            return retData;
        }
        String match = "Матч";
        String time = "Тайм";
        String propala = "пропала";
        if (score.contains(match)) {
            return retData;
        }
        try {
            int mid = score.indexOf('-');
            retData[1] = Integer.parseInt(score.substring(0,mid).trim());
            retData[2] = Integer.parseInt(score.substring(mid+1).trim());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(score);
        }
        return retData;
    }

    public int[][] parseFullScore(String score) {
        int[][] retData = new int[4][3];
        if (score.isEmpty()) {
            return retData;
        }
        String match = "Матч";
        String time = "Тайм";
        String propala = "пропала";
        if (score.contains(match)) {
            return retData;
        }
        try {
            int end = score.indexOf(')');
            if (score.contains(propala)) {
                for (int i =0 ; i<4; i++) {
                    for (int j =0 ; j<3; j++) {
                        retData[i][j] = -1;
                    }
                }
                return retData;
            }
            if (score.length()>3 && !score.contains(match) && !score.contains(time)) {
                score = score.substring(0,end).trim();
            }
            score = score.replace("(","").replace(")","").trim();
            int chatCount = spaceCount(score);
            retData[0][0] = chatCount;
            switch (chatCount) {
                case 1: {
                    if (score.length()>6 && !score.contains(match) && !score.contains(time)) {
                        int mid = score.indexOf('-');
                        int quarter2begin = score.indexOf(' ',1);
                        int quarter2mid = score.indexOf('-', quarter2begin);
                        retData[0][1] = Integer.parseInt(score.substring(0,mid).trim());
                        retData[0][2] = Integer.parseInt(score.substring(mid+1,quarter2begin).trim());
                        retData[1][1] = Integer.parseInt(score.substring(quarter2begin+1, quarter2mid).trim());
                        retData[1][2] = Integer.parseInt(score.substring(quarter2mid+1).trim());
                    }
                    break;
                }
                case 2: {
                    if (score.length()>10 && !score.contains(match) && !score.contains(time)) {
                        int mid = score.indexOf('-');
                        int quarter2begin = score.indexOf(' ',1);
                        int quarter2mid = score.indexOf('-', quarter2begin);
                        int quarter3begin = score.indexOf(' ', quarter2begin + 1);
                        int quarter3mid = score.indexOf('-', quarter3begin);
                        retData[0][1] = Integer.parseInt(score.substring(0,mid).trim());
                        retData[0][2] = Integer.parseInt(score.substring(mid+1,quarter2begin).trim());
                        retData[1][1] = Integer.parseInt(score.substring(quarter2begin+1, quarter2mid).trim());
                        retData[1][2] = Integer.parseInt(score.substring(quarter2mid+1,quarter3begin).trim());
                        retData[2][1] = Integer.parseInt(score.substring(quarter3begin, quarter3mid).trim());
                        retData[2][2] = Integer.parseInt(score.substring(quarter3mid+1).trim());
                    }
                    break;
                }
                case 3: {
                    if (score.length()>14 && !score.contains(match) && !score.contains(time)) {
                        int mid = score.indexOf('-');
                        int quarter2begin = score.indexOf(' ',1);
                        int quarter2mid = score.indexOf('-', quarter2begin);
                        int quarter3begin = score.indexOf(' ', quarter2begin + 1);
                        int quarter3mid = score.indexOf('-', quarter3begin);
                        int quarter4begin = score.indexOf(' ', quarter3begin + 1);
                        int quarter4mid = score.indexOf('-',quarter4begin);
                        retData[0][1] = Integer.parseInt(score.substring(0,mid).trim());
                        retData[0][2] = Integer.parseInt(score.substring(mid+1,quarter2begin).trim());
                        retData[1][1] = Integer.parseInt(score.substring(quarter2begin+1, quarter2mid).trim());
                        retData[1][2] = Integer.parseInt(score.substring(quarter2mid+1,quarter3begin).trim());
                        retData[2][1] = Integer.parseInt(score.substring(quarter3begin, quarter3mid).trim());
                        retData[2][2] = Integer.parseInt(score.substring(quarter3mid+1,quarter4begin).trim());
                        retData[3][1] = Integer.parseInt(score.substring(quarter4begin,quarter4mid).trim());
                        retData[3][2] = Integer.parseInt(score.substring(quarter4mid+1).trim());
                    }
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(score);
        }return retData;
    }

    public float parseTime(String time) {
        String match = "Матч не";
        String end = "Тайм";
        if (time.contains(match) || time.contains(end)) {
            return 0.0f;
        }
        if (time.contains("'")) {
            int endIndex = time.indexOf("'")-1;
            time = time.substring(0,endIndex);
            return Float.parseFloat(time);
        }
        if (time.isEmpty()) {
            return 0.0f;
        }
        time = time.replace(':','.').trim();
        return Float.parseFloat(time);
    }

    public String parseRef(String ref) {
        int start;
        start = ref.indexOf('/',22)+1;
        return ref.substring(start);
    }
}
