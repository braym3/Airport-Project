import React, {useState} from "react";

import { fetchChannels, fetchEpg, fetchEarliestFlightTime, fetchLastFlightTime } from "./helpers";

import { useEpg } from "planby";

// Import theme
import { theme } from "./helpers/theme";

export function useApp({timeJumpTriggered}) {
    const [channels, setChannels] = React.useState([]);
    const [epg, setEpg] = React.useState([]);
    const [isLoading, setIsLoading] = React.useState(false);
    const [scheduleStartTime, setScheduleStartTime] = useState(null);
    const [scheduleEndTime, setScheduleEndTime] = useState(null);

    const channelsData = React.useMemo(() => channels, [channels]);
    const epgData = React.useMemo(() => epg, [epg]);

    const { getEpgProps, getLayoutProps } = useEpg({
        channels: channelsData,
        epg: epgData,
        dayWidth: 17000,
        sidebarWidth: 110,
        itemHeight: 100,
        isSidebar: true,
        isTimeline: true,
        isLine: true,
        startDate: scheduleStartTime,
        endDate: scheduleEndTime,
        isBaseTimeFormat: true,
        theme
    });

    const handleFetchResources = React.useCallback(async () => {
        setIsLoading(true);
        const epg = await fetchEpg();
        const channels = await fetchChannels();
        const earliestFlightTime = await fetchEarliestFlightTime();
        const latestFlightTime = await fetchLastFlightTime();
        setEpg(epg);
        setChannels(channels);
        setScheduleStartTime(earliestFlightTime);
        setScheduleEndTime(latestFlightTime);
        setIsLoading(false);
    }, []);

    React.useEffect(() => {
        handleFetchResources();
    }, [timeJumpTriggered]);

    return { getEpgProps, getLayoutProps, isLoading };
}