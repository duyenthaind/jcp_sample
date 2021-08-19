package com.thaind.jcp.fraction6.timed_limit_task;

import java.util.*;
import java.util.concurrent.*;

public class QuoteTaskBudgetInvokeAll {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> comparator, long time, TimeUnit timeUnit) throws Exception {
        List<QuoteTask> listQuoteTasks = new ArrayList<>();
        companies.forEach(travelCompany -> listQuoteTasks.add(new QuoteTask(travelCompany, travelInfo)));

        List<Future<TravelQuote>> futures = executorService.invokeAll(listQuoteTasks,time, timeUnit);

        List<TravelQuote> listQuotes = new ArrayList<>(listQuoteTasks.size());
        Iterator<QuoteTask> iterator = listQuoteTasks.iterator();

        futures.forEach(travelQuoteFuture -> {
            QuoteTask task = iterator.next();
            try{
                listQuotes.add(travelQuoteFuture.get());
            } catch(ExecutionException executionException){
                listQuotes.add(task.getFailureQuote(executionException.getCause()));
            } catch(CancellationException cancellationException){
                listQuotes.add(task.getTimeoutQuote(cancellationException));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        listQuotes.sort(comparator);
        return listQuotes;
    }
}

class QuoteTask implements Callable<TravelQuote> {

    private final TravelCompany travelCompany;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany travelCompany, TravelInfo travelInfo) {
        this.travelCompany = travelCompany;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable throwable) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException exception) {
        return null;
    }

    @Override
    public TravelQuote call() throws Exception {
        return travelCompany.solicitQuote(travelInfo);
    }
}

interface TravelQuote {

}

interface TravelInfo {

}

interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}