import React, from "react";
import "./styles.css";
import { Epg, Layout } from "planby";
import { useApp } from "./useApp";
import { Timeline, ChannelItem, ProgramItem } from "./components";

function GateSchedule({timeJumpTriggered}) {
    const { isLoading, getEpgProps, getLayoutProps } = useApp({timeJumpTriggered});

    return (
        <div>
            <header style={{paddingTop: '30px'}} >
                <h3>Gate Schedules</h3>
            </header>
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