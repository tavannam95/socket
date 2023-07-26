package com.a2m.mail.server.handler;
import java.util.ArrayList;

import javax.mail.Flags;
import javax.mail.Flags.Flag;

import com.a2m.mail.shared.data.MessageImpl.IMAPFlag;

/**
 * Util class which helps to convert from hupa internal data representation to javamaill classes
 *
 */
public class JavamailUtil {

    /**
     * Convert the given Flags to a ArrayList of IMAPFlags
     *
     * @param flags
     * @return imapFlags
     */
    public static ArrayList<IMAPFlag> convert(Flags flags) {
        ArrayList<IMAPFlag> fList = new ArrayList<IMAPFlag>();
        for (Flag flag : flags.getSystemFlags()) {
            fList.add(convert(flag));
        }
        return fList;

    }

    public static IMAPFlag convert(Flag flag) {
        if (flag.equals(Flag.SEEN)) {
            return IMAPFlag.SEEN;
        } else if (flag.equals(Flag.RECENT)) {
            return IMAPFlag.RECENT;
        } else if (flag.equals(Flag.ANSWERED)) {
            return IMAPFlag.ANSWERED;
        } else if (flag.equals(Flag.DELETED)) {
            return IMAPFlag.DELETED;
        } else if (flag.equals(Flag.DRAFT)) {
            return IMAPFlag.DRAFT;
        } else if (flag.equals(Flag.FLAGGED)) {
            return IMAPFlag.FLAGGED;
        } else if (flag.equals(Flag.USER)) {
            return IMAPFlag.USER;
        }

        throw new IllegalArgumentException("Flag not supported " + flag);
    }
    /**
     * Convert the given ArrayList of IMAPFlags to a Flags object
     *
     * @param imapFlags
     * @return flags
     */
    public static Flags convert(ArrayList<IMAPFlag> imapFlags) {
        Flags iFlags = new Flags();
        for (IMAPFlag flag : imapFlags) {
            iFlags.add(convert(flag));
        }
        return iFlags;
    }

    public static Flag convert(IMAPFlag flag) {
        if (flag.equals(IMAPFlag.SEEN)) {
            return Flag.SEEN;
        } else if (flag.equals(IMAPFlag.RECENT)) {
            return Flag.RECENT;
        } else if (flag.equals(IMAPFlag.ANSWERED)) {
            return Flag.ANSWERED;
        } else if (flag.equals(IMAPFlag.DELETED)) {
            return Flag.DELETED;
        }
        throw new IllegalArgumentException("Flag not supported");

    }
}
