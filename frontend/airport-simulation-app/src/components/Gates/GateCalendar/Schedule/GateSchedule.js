import React, {useState} from "react";
import "./styles.css";
import { Epg, Layout } from "planby";
import InfoModal from "./components/InfoModal";

// Import hooks
import { useApp } from "./useApp";

// Import components
import { Timeline, ChannelItem, ProgramItem } from "./components";

function GateSchedule({timeJumpTriggered}) {
    const { isLoading, getEpgProps, getLayoutProps } = useApp({timeJumpTriggered});

    return (
        <div>
            <div style={{ paddingTop: "20px",height: "90vh", width: "100%" }}>
                <Epg isLoading={isLoading} {...getEpgProps()}>
                    <Layout
                        {...getLayoutProps()}
                        renderTimeline={(props) => <Timeline {...props} />}
                        renderProgram={({ program, ...rest }) => (
                            <ProgramItem key={program.data.id} program={program} {...rest} />
                        )}
                        renderChannel={({ channel }) => (
                            <ChannelItem key={channel.uuid} channel={channel} />
                        )}
                    />
                </Epg>
            </div>
        </div>
    );
}

export default GateSchedule;