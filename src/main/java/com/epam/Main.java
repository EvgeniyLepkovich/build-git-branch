package com.epam;

import com.epam.model.GitRepository;
import com.epam.model.feedback.Feedback;
import com.epam.model.feedback.Message;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static String CURRENT_GIT_PATH;
    private static String CURRENT_GIT_BRANCH;
    private static String CURRENT_PATH_TO_POM;
    private static String pathSh = "src/main/resources/runShell.bat";

    public static void main(String[] args) {
        determineArgs(args);
        try {
            GitRepository gitRepository = new GitRepository(CURRENT_GIT_PATH);
            ArrayList<RevCommit> revCommits = gitRepository.getCommitsSSH(CURRENT_GIT_BRANCH, true);
            Feedback feedback = new Feedback();
            for(RevCommit commit : revCommits) {
                gitRepository.checkout(commit.getName());
                Process process = new ProcessBuilder(pathSh, CURRENT_PATH_TO_POM).start();

                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String str = null;
                Message message = new Message();
                message.setCommit(commit);
                while ((str = stdInput.readLine()) != null) {
                    if (str.equals("Build Successful") || str.equals("Build Failed")) {
                        message.setResult(str);
                    }
                    if (str.equals("0") || str.equals("1")) {
                        message.setStatus(Integer.parseInt(str));
                    }
                    System.out.println(str);
                }
                message.setOutput(str);
                feedback.putMessage(message);
            }
            gitRepository.checkout(CURRENT_GIT_BRANCH);
            System.out.println(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static void determineArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        for (String arg : args) {
            String[] pairs = arg.split("=");
            argsMap.put(pairs[0], pairs[1]);
        }
        putArgsIntoFields(argsMap);
    }

    private static void putArgsIntoFields(Map<String, String> argsMap) {
        CURRENT_PATH_TO_POM = argsMap.get("-Dpom").toString();
        CURRENT_GIT_PATH = argsMap.get("-Dgit").toString();
        CURRENT_GIT_BRANCH = argsMap.get("-Dbranch").toString();
    }
}
