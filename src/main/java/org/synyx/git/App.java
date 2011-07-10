package org.synyx.git;

import java.io.IOException;
import java.util.Iterator;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException, WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException {
        CloneCommand clone = new CloneCommand();
        Git git = clone.setURI("http://git.synyx.org/opencms-solr-module.git").call();

        git.pull().call();
        Iterable call = git.log().call();
        Iterator it = call.iterator();
        while (it.hasNext()) {
            RevCommit o = (RevCommit) it.next();
            System.out.println(o.getAuthorIdent().getName() + ": " + o.getShortMessage());
        }

    }
}
